const request = require('request');
const bodyParser = require('body-parser');
const util = require('util');
var debug = require('debug')('ng-app:server');
var http = require('http');
var express = require('express');
var path = require('path');
const app = express();


app.use(bodyParser.json());
app.use(bodyParser.urlencoded({'extended': 'false'}));
app.use(express.static(path.join(__dirname, 'dist')));
module.exports = app;

const googleAPIkey = "Hidden Google API Key";
const darkskyAPIkey = "Hidden Darksky API Key";

// GET request to Google Places Autocomplete API in the format: /autoComplete?input=##
// Autocomplete the input and return the matching cities
//Autocomplete changed to return JSON response of all autocomplete matching addresses (including street city country)
app.get('/autoComplete', (req, res) => {
    let currentInput = encodeURIComponent(req.query.input.toString());
    let URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + currentInput + "&types=(cities)&language=en&key=" + googleAPIkey + "&region=us";
    
    request(URL, {json: true}, function(error, response, body) {
        if (body.status === "OK" ) {
            let cities = [];
            for(let i = 0; i < body.predictions.length; i++) {
                cities.push(body.predictions[i].structured_formatting.main_text);
            }
            console.log(cities);
            // res.send(body);
            res.send(cities);

        } else {
            res.send("");
        }
    })
})


// GET request to Google Maps Geocode API in the format: /addressCoordinates?street=##&city=##&state=##
// State needs to be its two letter abbreviation
app.get('/addressCoordinates', (req, res) => {
    console.log(req.query.street.toString());
    console.log(req.query.city.toString());
    console.log(req.query.state);

    // let street = req.query.street.toString();
    // let city = req.query.city.toString();
    // let state = req.query.state.toString();
    let address = encodeURIComponent(req.query.street + ", " + req.query.city + ", "  + req.query.state);
    let encodedURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + googleAPIkey;
    request(encodedURL, {json: true}, function(error, response, body) {
        console.error('error:', error);
        console.log('statusCode:', response && response.statusCode);
        if (body.status === "OK" && typeof body.results[0].partial_match === 'undefined') {
            req.lat = body.results[0].geometry.location.lat;
            req.lng = body.results[0].geometry.location.lng;
            console.log(body.results[0].geometry.location);
            res.send(body.results[0].geometry.location);
        } else {
            res.send("Error invalid results");
        }
    });
});


// GET request to Darksky API weather forecast in the format: /getCurrentWeather?lat=##&lng=##
app.get('/getCurrentWeather', (req, res) => {
    //console.log("appJS req value is: " + util.inspect(req));
    console.log("appJS /getCurrentWeather value of lat: " + req.query.lat);
    console.log("appJS /getCurrentWeather value of lng: " + req.query.lng);
    let URL = "https://api.darksky.net/forecast/" + darkskyAPIkey + "/" + req.query.lat + "," + req.query.lng;
    console.log('appJS /getCurrentWeather value of URL is: ' + URL);

    request(URL, function(error, response, body) {
        console.error('error:', error);
        console.log('statusCode:', response && response.statusCode);
        res.send(body);
    });
})


// GET request to Google CustomSearch for searching image of State city seal in the format: /getStateSeal?state=##
app.get('/getStateSeal', (req, res) => {
    console.log("appJS /getStateSeal value of state: " + req.query.state);

    let URL = "https://www.googleapis.com/customsearch/v1?q=Seal%20of%20" + req.query.state.toString() 
    + "%20State&cx=013503531998323216086:a9zxjglx49t&imgSize=xxlarge&num=1&searchType=image&key=" + googleAPIkey;

    request(URL, {json: true}, function(error, response, body) {
        console.error('error:', error);
        console.log('statusCode:', response && response.statusCode);

        let result = {
            stateSealLink: body.items[0].link
        }
        console.log(result);
        res.send(result);
    });
})


// GET request to Google CustomSearch for getting city images in the format: /getCityPhotos?address=##
app.get('/getCityPhotos', (req, res) => {
    let URL = "https://www.googleapis.com/customsearch/v1?q=" + req.query.address.toString() 
    + "&cx=013503531998323216086:a9zxjglx49t&imgSize=xlarge&num=8&searchType=image&key=" + googleAPIkey;

    request(URL, {json: true}, function(error, response, body) {
        console.error('error:', error);
        console.log('statusCode:', response && response.statusCode);
        console.log(body);
        console.log(body.items[0].link);
        res.send(body);
        //res.send(body.items.link);
    });
})


var port = (process.env.PORT || '8080');
app.set('port', port);

app.use(express.static(path.join(__dirname, 'dist/ng-app')));

/*
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname + 'dist/ng-app/index.html'));
});
*/

var server = http.createServer(app);
server.listen(port);
server.on('listening', onListening);

function onListening() {
    var addr = server.address();
    debug('Listening on ' + port);
}