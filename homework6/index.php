<?php
if($_POST["latitude"]){
/* PHP server content
 POST request for detailed weather info */
  define('DarkskyApiKey','Hidden API Key'); //Modified API key for safety
  $DarkskyApiKey = DarkskyApiKey;
  $url_latitude = $_POST['latitude'];
  $url_longitude = $_POST['longitude'];
  $url_time = $_POST['time'];
  $json_detailed_resp = file_get_contents("https://api.darksky.net/forecast/" . $DarkskyApiKey . "/{$url_latitude},{$url_longitude},{$url_time}?exclude=minutely");
  echo $json_detailed_resp;
}

/* POST request for coordinates corresponding to an address */
 elseif($_POST["addressURL"]){
$xml_coord_resp = file_get_contents($_POST["addressURL"]);
echo $xml_coord_resp;
}

/* POST request for general weather info */
 elseif($_POST["weatherURL"]){
$json_weather_resp = file_get_contents($_POST["weatherURL"]);
echo $json_weather_resp;
}
/* HTML and CSS page content */
else{
  ?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Weather Search</title>
  <style>

  /* Input box formatting */
  #box-form {
    border-radius:20px;
    background: #32AB38;
    margin:auto;
    margin-top:42px;
    height: 275px;
    width: 880px;
    margin-bottom: 30px;
    line-height: 1;
  }

  #page-title{
    color: white;
    font-style:italic;
    text-align: center;
    font-weight: normal;
    font-size: 37pt;
    margin-bottom: 0;
    padding-top: 10px
  }

  #input_fields {
    margin-top:2px;
    padding-left:94px;
    height:135px;
    color:white;
    font-size: 14.5pt;
    font-weight: bold;
  }

  .inputLabel {
    margin-left: -32px;
  }

  #street, #city {
    padding:3px 0;
  }

  #city {
    margin-left: 13.5px;
    margin-top: 12px;
  }

  #state {
    margin-top: 15px;
    margin-left: 2px;
    width: 235px;
  }

  #current_loc {
    margin-left: 125px;
  }

  #left-column, #right-column {
    display: inline-block;
    height:135px;
    vertical-align: top;
  }

  #mid-column {
    margin: 0px 0 0 136px;
    padding: 0;
    display: inline-block;
    width: 0;
    height: 135px;
    border: 2.5px solid white;
    border-radius: 2px;
  }

  #buttons_id {
    display: inline-block;
    margin: 25px 0 0 327px;
  }

  .submitButton {
    margin:0 5px 0 0;
    padding:2px 7.5px 2px 7.5px;
    font-size: 10pt;
    border: 1px solid white;
    background-color: white;
    border-radius: 5px;
  }

/* Invalid input message formatting */
  #invalidTextContainer {
    margin: auto;
    margin-top: 25px;
    width: 460px;
    background-color: #F2F2F4;
    border-style: solid;
    border-width: 3px;
    border-color: #A9A9A9;
  }

  #invalidAddress {
    margin:4.5px auto 0 auto;
    text-align: center;
    font-size: 17pt;
  }

/* Current weather card formatting */
  #currentSummary, #summaryCity, #summaryTimeZone, #summaryTemperatureBox, #summaryTemperature, #summarySummary, #summaryTemperatureUnit, #currentSummaryInnerBox {
    margin:0;
    color: white;
    background-color: inherit;
    border-radius:20px;
  }

  #summaryTemperatureBox {
    height: 142px;
    width: 550px;
    vertical-align: middle;
  }

  #currentSummary {
    font-size: 0;
    border-radius:20px;
    background: #26c7fb;
    margin: 36.5px auto;
    height: 385px;
    width: 580px;
  }

  #currentSummaryInnerBox {
    padding-left: 24px;
    font-size: 0;
  }

  #summaryCity {
    font-size: 29pt;
    padding-top: 31px;
  }

  #summaryTimeZone {
    font-size: 13.5pt;
    padding-top: 1px;
  }

  #summaryTable {
    font-size: 0;
    width: 560px;
    margin: 10px auto auto -9px;
    padding-bottom: 15px;
    text-align: center;
    color:white;
    background-color: inherit;
    border-radius:20px;
  }

  .summaryTableIcon {
    font-size: 0;
    width :34px;
    height :34px;
  }

  #summaryTemperature, #summaryTemperaturePicture, #summaryTemperatureUnit {
    margin:0;
    text-align: center;
    display: inline-block;
    background-color: inherit;
  }

  #summaryTemperature, #summarySummary, .summaryTableValue {
    font-weight: bold;
  }

  #summaryTemperature {
    margin:0;
    padding:0;
    text-align: center;
    vertical-align: middle;
    font-size: 85pt;
  }

  #summaryTemperaturePicture {
    width :17px;
    height :17px;
    padding: 30px 8px;
  }

  #summaryTemperatureUnit {
    font-size: 40pt;
    font-weight: bold;
    vertical-align: bottom;
    padding-bottom: 15px;
  }

  #summarySummary {
    font-size: 34pt;
  }

  .summaryTableValue {
    font-size: 21pt;
    background-color: inherit;
    border-radius:20px;
  }

  /* Weekly weather table formatting */
  #weeklyTable {
    margin: 35px auto;
    border: 1px solid #379CC5;
    border-collapse: collapse;
    background-color: #A0CBEE;
    font-size: 0;
    width: 1050px;
  }

  #weeklyTable th, #weeklyTable tr, #weeklyTable td {
    border: 2.5px solid #379CC5;
    font-size: 16pt;
    font-weight: bold;
    color: white;
    text-align: center;
    height:50px;
  }

  #weeklyTable img {
    width:50px;
    height:50px;
  }

  .weeklyTableCell {
    white-space: nowrap;
  }

  .summaryLink {
    text-decoration: none !important;
    color:white;
    white-space: normal;
    word-wrap: anywhere;
  }

  .detailedCardTitle {
    text-align: center;
    font-size: 33pt;
    margin: 20px auto 10px auto;
  }

  /* Detailed weather card */
  #detailedCardboxSummary, #detailedCardTextSummary, #detailedCardTemperature, #detailedTemperatureDegreePicture, #DetailedTemperatureUnit, #detailedTemperaturePicture{
    display: inline-block;
    margin:0;
    padding:0;
    line-height: 1;
  }

  #detailedCardboxSummary {
    margin: 82px 0 15px 25px;
  }

  #detailedCardTemperature {
    font-size: 104pt;
    font-weight: bold;
  }

  #DetailedTemperatureUnit {
    font-size: 80pt;
    font-weight: bold;
  }

  #detailedCardBox {
    height: 555px;
    width: 660px;
    border-radius:20px;
    background-color: #A9D1DB;
    margin: 30px auto;
    color: white;
    position: relative;
  }

  #detailedCardTextSummary {
    margin: 0px 0 0 0;
    padding:0;
    font-size: 24pt;
    font-weight: bold;
    font-size: 30pt;
    word-wrap: break-word;
    width: 250px;
  }

  #detailedTemperatureDegreePicture {
    width :17px;
    height :17px;
    padding: 30px 8px 86px 8px;
  }

  #detailedTemperaturePicture {
    width: 305px;
    height: 305px;
    float: right;
    margin: -15px 25px 0 0;
  }

  .detailsDescription {
    font-size: 18pt;
    font-weight: bold;
    margin: 1px 1px 16px 0;
  }

  .detailsValue {
    font-size: 25pt;
    font-weight: bold;
    margin: 0 5px 6px 5px;
  }

  .detailsExtras {
    font-size: 16pt;
    font-weight: bold;
  }

  #detailedTemperatureDetails {
    clear: both;
    text-align: center;
    position: absolute;
    top: 300px;
    left: 237px;
    line-height: 1;
  }

  .collapsible {
    background-color: white;
    color: white;
    cursor: pointer;
    border: none;
    font-size: 0;
    line-height: 0;
    display: block;
    margin: 0 auto;
    outline: none;
  }

  .collapsible img {
    width: 58px;
    height: 58px;
  }

  #chart_div {
    display: none;
    margin: 0px auto 35px auto;
    width: 800px;
  }

  #detailsLeft {
    display:inline-block;
    float:left;
    text-align: right;
  }

  #detailsRight {
    display: inline-block;
    float: right;
    text-align: left;
  }
  </style>

</head>
<body>
  <div id="box-form">
    <h1 id="page-title">Weather Search</h1>
    <form id="myForm" name="myForm" action="#" method = "GET" >
      <div id="input_fields">
        <div id="left-column">
          <label class="inputLabel" for="street">Street</label>
          <input name="street" id="street" type="text" value="<?php echo isset($_GET["street"])? $_GET["street"] : ""; ?>"/> <br/>
          <label class="inputLabel" for="city">City</label>
          <input name="city" id="city" type="text" value="<?php echo isset($_GET["city"])? $_GET["city"] : ""; ?>"/><br/>
          <label class="inputLabel" for="state">State</label>
          <select id="state" name="state">
            <option value="" "<?php echo (isset($_GET['state']) ? '' : ' selected'); ?>" >State</option>
            <option value=""  style="color:black" disabled>-------------------------</option>
          </select>
        </div>
        <div id="mid-column">
        </div>
        <div id="right-column">
          <input type="checkbox" id="current_loc" name="current_loc" onclick="currentLocationChecked()" />
          <label for="current_loc">Current Location</label>
        </div>
      </div>
      <div id="buttons_id"><input class="submitButton" type="button" name="submit" onClick="validateAndSubmit()" value="search"/><input class="submitButton" type="button" onClick='resetButton()' value="clear"/></div>
    </form>
  </div>
</body>

<!-- JS content -->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">

// Removes all content beyond first div, i.e. input form
function deleteResultBoxes() {
  var resultBoxes = document.getElementsByClassName("resultBox");
  let i;
  for (i= 0; i < resultBoxes.length; i++) {
    document.body.removeChild(resultBoxes[i]);
  }
  var weeklyTableWrapper = document.getElementById('weeklyTableWrapper');
  if (weeklyTableWrapper != null) document.body.removeChild(weeklyTableWrapper);
}


// Reset input form to default values
function defaultInputForms() {
  document.getElementById("street").disabled = false;
  document.getElementById("city").disabled = false;
  document.getElementById("state").disabled = false;
  document.getElementById("street").value = "";
  document.getElementById("city").value = "";
  document.getElementById("state").value = "";
  document.getElementById("current_loc").checked = false;
  let labels = document.getElementsByClassName("inputLabel");
  for (let i = 0; i < labels.length; i++) {
    labels[i].style.opacity = "1";
  }
  document.getElementById("street").style.opacity = "1";
  document.getElementById("city").style.opacity = "1";
  document.getElementById("state").style.opacity = "1";
}
defaultInputForms(); // When page is refreshed reset form values


// Reset button: resets form to default and remove any previously generated content
function resetButton() {
  defaultInputForms();
  deleteResultBoxes();
  var invalidTextContainer = document.getElementById("invalidTextContainer");
  if (invalidTextContainer != null) {
    document.body.removeChild(invalidTextContainer);
  }
}


// Toggles between showing and hiding the chart while changing arrow image
function toggleChart() {
  let arrow = document.getElementsByClassName("collapsible")[0];
    var content = arrow.nextElementSibling;
    if (content.style.display === "block") {
      content.style.display = "none";
      let arrowElement = document.getElementById("arrowImage");
      arrowElement.src = "https://cdn4.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png";
    } else {
      content.style.display = "block";
      let arrowElement = document.getElementById("arrowImage");
      arrowElement.src = "https://cdn0.iconfinder.com/data/icons/navigation-set-arrows-part-one/32/ExpandLess-512.png";
    }
}


// Finds latitude and longitude based on address inputted by user
function findAddress(address) {
  const GoogleGeocodeApiKey = "Hidden API Key"; //Modified API key for safety
  address = encodeURIComponent(address);
  let url = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + address + "&key=" + GoogleGeocodeApiKey;

  let xmlhttp = new XMLHttpRequest();
  xmlhttp.open("POST", "#", false);
  formData = new FormData();
  formData.append('addressURL', url);
  xmlhttp.send(formData);

  if (xmlhttp.status == 200) {
    let xmlText = xmlhttp.responseText;
    let parser = new DOMParser();
    xmlDoc = parser.parseFromString(xmlText, "text/xml");
    return xmlDoc;
  }
}


// Populates weekly table with data from json file and shows the summary card
function populateTableSummary(jsonObj, cityName) {
  cityName = cityName.toLowerCase().split(' ').map((s) => s.charAt(0).toUpperCase() + s.substring(1)).join(' ');
  var htmlText = "";

  htmlText += "<div id='currentSummary'><div id='currentSummaryInnerBox'>";
  htmlText += "<h3 id='summaryCity'>" + cityName + "</h3><p id='summaryTimeZone'>" + jsonObj.timezone + "</p>";
  htmlText += "<div id='summaryTemperatureBox'><p id='summaryTemperature'>" + jsonObj.currently.temperature + "</p>";
  htmlText += "<img id='summaryTemperaturePicture' src='https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png' />";
  htmlText += "<p id='summaryTemperatureUnit'>F</p></div>";
  htmlText += "<p id='summarySummary'>" + jsonObj.currently.summary + "</p><table id='summaryTable'><tr>"
  htmlText += "<td><img class='summaryTableIcon' src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-16-512.png' alt='Humidity' title='Humidity'/></td>";
  htmlText += "<td><img class='summaryTableIcon' src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-25-512.png' alt='Pressure' title='Pressure'/></td>";
  htmlText += "<td><img class='summaryTableIcon' src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png' alt='Wind Speed' title='Wind Speed'/></td>";
  htmlText += "<td><img class='summaryTableIcon' src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-30-512.png' alt='Visibility' title='Visibility'/></td>";
  htmlText += "<td><img class='summaryTableIcon' src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png' alt='Cloud Cover' title='Cloud Cover'/></td>";
  htmlText += "<td><img class='summaryTableIcon' src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-24-512.png' alt='Ozone' title='Ozone'/></td>";
  htmlText += "</tr><tr><td class ='summaryTableValue'>" + jsonObj.currently.humidity + "</td><td class ='summaryTableValue'>" + jsonObj.currently.pressure + "</td>";
  htmlText += "<td class ='summaryTableValue'>" + jsonObj.currently.windSpeed + "</td><td class ='summaryTableValue'>" + jsonObj.currently.visibility+ "</td>";
  htmlText += "<td class ='summaryTableValue'> "+ jsonObj.currently.cloudCover + "</td><td class ='summaryTableValue'>" + jsonObj.currently.ozone + "</td>";
  htmlText += "</tr></table></div></div><div id='weeklyTableWrapper' class='resultBox'>";
  htmlText += "<table id='weeklyTable'><tr><th>Date</th><th>Status</th><th>Summary</th><th>TemperatureHigh</th><th>TemperatureLow</th><th>Wind Speed</th></tr>";

  let tableRows = jsonObj.daily.data;
  tableRows.forEach(function(row) {
    let s = new Date(row.time*1000); // Multiply by 1000 to get milliseconds
    let time = s.toLocaleDateString('en-US', {timeZone: jsonObj.timezone, year: 'numeric', month:'2-digit', day:'2-digit'});
    time = time.replace(/\//g, "-");
    htmlText += "<tr><td class='weeklyTableCell'>" + time + "</td>";

    if (row.icon === "clear-day" || row.icon === "clear-night") {
      htmlText += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png' /></td>";
    } else if (row.icon === "rain") {
      htmlText += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-04-512.png' /></td>";
    } else if (row.icon === "snow") {
      htmlText += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-19-512.png' /></td>";
    } else if (row.icon === "sleet") {
      htmlText += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-07-512.png' /></td>";
    } else if (row.icon === "wind") {
      htmlText += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png' /></td>";
    } else if (row.icon === "fog") {
      htmlText += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png' /></td>";
    } else if (row.icon === "cloudy") {
      htmlText += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-01-512.png' /></td>";
    } else if (row.icon === "partly-cloudy-day" || row.icon === "partly-cloudy-night") {
        htmlText += "<td><img src='https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png' /></td>";
      }

      htmlText += "<td class='weeklyTableCell'><a class='summaryLink' href='#' onclick='getDetailedJSON(" + jsonObj.latitude + ','
      + jsonObj.longitude + ',' + row.time + ")'>" + row.summary + "</a></td>";

      htmlText += "<td class='weeklyTableCell'>" +  row.temperatureHigh  + "</td>";
      htmlText += "<td class='weeklyTableCell'>" +  row.temperatureLow  + "</td>";
      htmlText += "<td class='weeklyTableCell'>" +  row.windSpeed  + "</td>";

      htmlText += "</tr>";
  });

  htmlText += "</table></div><div id='detailedCardBoxWrapper' class='resultBox'></div></div>";

  //remove everything and repopulate with new html
  var resultWrapper = document.getElementById("currentSummaryWrapperBox");
  if (resultWrapper != null) {
    document.body.removeChild(resultWrapper);
  }

  var invalidTextContainer = document.getElementById("invalidTextContainer");
  if (invalidTextContainer != null) {
    document.body.removeChild(invalidTextContainer);
  }
    let div = document.createElement("div");
    div.setAttribute("id", "currentSummaryWrapperBox");
    div.setAttribute("class", "resultBox");
    div.innerHTML = htmlText;
    document.body.appendChild(div);
}


// Does POST request and populates the html with resulting data
function formSubmit(formData) {
  let city;
  let latitude;
  let longitude;

  if (formData === undefined) {
    // call ip-api to get location from ip address
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "http://ip-api.com/json", false);
    xmlhttp.send();
    let jsonObj = JSON.parse(xmlhttp.responseText);
    city = jsonObj.city;
    latitude = jsonObj.lat;
    longitude = jsonObj.lon;
  } else {
    city = formData.get("city");
    let street = formData.get("street");
    let state = formData.get("state");
    let address = street + "," + city + "," + state;
    let xmlDoc = findAddress(address);
    latitude = xmlDoc.getElementsByTagName("lat")[0].childNodes[0].nodeValue;
    longitude = xmlDoc.getElementsByTagName("lng")[0].childNodes[0].nodeValue;
  }

  const DarkskyApiKey = "Hidden API Key"; //Modified API key for safety
  let xmlhttp = new XMLHttpRequest();
  xmlhttp.open("POST", "#", false);
  let url = "https://api.forecast.io/forecast/" + DarkskyApiKey + "/" + latitude + "," + longitude + "?exclude=minutely,hourly,alerts,flags";
  urlFormData = new FormData();
  urlFormData.append("weatherURL", url);
  xmlhttp.send(urlFormData);

  if (xmlhttp.status == 200) {
    let jsonObj = JSON.parse(xmlhttp.responseText);
    populateTableSummary(jsonObj, city);
  }
}


// Validates and if valid submits otherwise shows an error message
function validateAndSubmit() {
  let locationChecked = document.getElementById("current_loc").checked;
  if(document.forms["myForm"]["street"].value == "" || document.forms["myForm"]["city"].value == "" || document.forms["myForm"]["state"].value == "") {
    if (!locationChecked) {
      deleteResultBoxes();
      let errorBox = document.createElement("div");
      errorBox.setAttribute("id", "invalidTextContainer")
      let invalidAddress = document.getElementById('invalidAddress');
      if (invalidAddress == null) {
        errorBox.innerHTML = "<p id ='invalidAddress'>Please check the input address.</p>";
        document.body.appendChild(errorBox);
      }
    } else {
      // Use submit with no paramaters to use ip-api
      formSubmit();
    }
  } else {
    //submit with user inputted address
    let formData = new FormData();
    formData.append('street', document.forms["myForm"]["street"].value);
    formData.append('city', document.forms["myForm"]["city"].value);
    formData.append('state', document.forms["myForm"]["state"].value);
    formSubmit(formData);
  }
}


// Toggles between disabled and enabled address input forms
function currentLocationChecked() {
  document.getElementById("street").value = "";
  document.getElementById("city").value = "";
  document.getElementById("state").value = "";
  if (document.getElementById("street").disabled == false) {
    document.getElementById("street").disabled = true;
    document.getElementById("city").disabled = true;
    document.getElementById("state").disabled = true;
    let labels = document.getElementsByClassName("inputLabel");
    for (let i = 0; i < labels.length; i++) {
      labels[i].style.opacity = "0.8";
    }
    document.getElementById("street").style.opacity = "0.8";
    document.getElementById("city").style.opacity = "0.8";

  }else {
    defaultInputForms();
  }
}


// Populate detailed card clicked from the weekly table
function populateDetailedCard(json_response) {
  let divId = document.getElementById("detailedCardBoxWrapper");
  let htmlText = "<h2 class='detailedCardTitle'>Daily Weather Detail</h2><div id='detailedCardBox'><div id='detailedCardboxSummary'><p id='detailedCardTextSummary'>"
  + json_response.currently.summary + "</p><br/>" + "<p id='detailedCardTemperature'>"+ Math.round(json_response.currently.temperature) + "</p>"
  + "<img id='detailedTemperatureDegreePicture' src='https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png'/><p id='DetailedTemperatureUnit'>F</p></div>";
  let iconText = json_response.currently.icon;
  if (iconText == "clear-day" || iconText == "clear-night")
    htmlText += "<td><img id='detailedTemperaturePicture' src='https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png'/></td>";
  else if (iconText == "rain")
    htmlText += "<td><img id='detailedTemperaturePicture' src='https://cdn3.iconfinder.com/data/icons/weather-344/142/rain-512.png'/></td>";
  else if (iconText == "snow")
    htmlText += "<td><img id='detailedTemperaturePicture' src='https://cdn3.iconfinder.com/data/icons/weather-344/142/snow-512.png'/></td>";
  else if (iconText == "sleet")
    htmlText += "<td><img id='detailedTemperaturePicture' src='https://cdn3.iconfinder.com/data/icons/weather-344/142/lightning-512.png'/></td>";
  else if (iconText == "wind")
    htmlText += "<td><img id='detailedTemperaturePicture' src='https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_10-512.png'/></td>";
  else if (iconText == "fog")
    htmlText += "<td><img id='detailedTemperaturePicture' src='https://cdn3.iconfinder.com/data/icons/weather-344/142/cloudy-512.png'/></td>";
  else if (iconText == "cloudy")
      htmlText += "<td><img id='detailedTemperaturePicture' src='https://cdn3.iconfinder.com/data/icons/weather-344/142/cloud-512.png'/></td>";
  else if (iconText == "partly-cloudy-day" || iconText == "partly-cloudy-night")
      htmlText += "<td><img id='detailedTemperaturePicture' src='https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png'/></td>";

  htmlText += "<div id='detailedTemperatureDetails'>";
  htmlText += "<div id='detailsLeft'>";

  htmlText += "<p class='detailsDescription'>Precipitation:</p>";

  htmlText += "<p class='detailsDescription'>Chance of Rain:</p>";

  htmlText += "<p class='detailsDescription'>Wind Speed:</p>";

  htmlText += "<p class='detailsDescription'>Humidity:</p>";

  htmlText += "<p class='detailsDescription'>Visibility:</p>";

  htmlText += "<p class='detailsDescription'>Sunrise / Sunset:</p>";
  htmlText += "</div>";

  htmlText += "<div id='detailsRight'>";
  let precipIntensity = json_response.currently.precipIntensity;

  if (precipIntensity <= 0.001)
    htmlText += "<p class='detailsValue'>None</p>";
  else if (precipIntensity <= 0.015)
    htmlText += "<p class='detailsValue'>Very Light</p>";
  else if (precipIntensity <= 0.05)
    htmlText += "<p class='detailsValue'>Light</p>";
  else if (precipIntensity <= 0.1)
    htmlText += "<p class='detailsValue'>Moderate</p>";
  else if (precipIntensity > 0.1)
    htmlText += "<p class='detailsValue'>heavy</p>";

    htmlText += "<p class='detailsValue'>" + Math.round(((json_response.currently.precipProbability*100)*100))/100 + "<span class='detailsExtras'>%</span></p>";

    htmlText += "<p class='detailsValue'>" + json_response.currently.windSpeed + "<span class='detailsExtras'>mph</span></p>";

    htmlText += "<p class='detailsValue'>" + Math.round(((json_response.currently.humidity*100)*100))/100 +"<span class='detailsExtras'>%</span></p>";

    htmlText += "<p class='detailsValue'>" + json_response.currently.visibility + "<span class='detailsExtras'>mi</span></p>";

    let sunsetTime = json_response.daily.data[0].sunsetTime;
    let s = new Date(sunsetTime*1000); // Mulitply by 1000 to get milliseconds
    sunsetTime = s.toLocaleDateString('en-US', {timeZone: json_response.timezone, hour: '2-digit'}).substr(-5);

    let sunriseTime = json_response.daily.data[0].sunriseTime;
    let r = new Date(sunriseTime*1000);
    sunriseTime = r.toLocaleDateString('en-US', {timeZone: json_response.timezone, hour: '2-digit'}).substr(-5);

    htmlText += "<p class='detailsValue'>" + parseInt(sunriseTime.substr(0, 2))
    + "<span class='detailsExtras'>" + sunriseTime.substr(-2) + "/</span>"
    + "<span class='detailsValue'>" + parseInt(sunsetTime.substr(0, 2)) + "</span>"
    + "<span class='detailsExtras'>" + sunsetTime.substr(-2) + "</span></p>";  // Sunrise / Sunset
    htmlText += "</div>"

  htmlText += "</div>";
  htmlText += "</div>";

  htmlText += "<h2 class='detailedCardTitle'>Day's Hourly Weather</h2>";

  htmlText += "<button type='button' class='collapsible' onclick='toggleChart()'><img id='arrowImage' src='https://cdn4.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png'/></button>";

  htmlText += "<div id='chart_div'></div>";

  google.charts.load('current', {packages: ['corechart', 'line']});
  google.charts.setOnLoadCallback(drawCrosshairs);


  // Draw chart
  function drawCrosshairs() {
        var data = new google.visualization.DataTable();
        data.addColumn('number', 'X');
        data.addColumn('number', 'T');
        let hourlyJsonData = [];

        for (let i = 0; i < json_response.hourly.data.length; i++) {
          let currentHourData = [i, json_response.hourly.data[i].temperature];
          hourlyJsonData[i] = currentHourData;
        }
        data.addRows(hourlyJsonData);

        var options = {
          hAxis: {
            title: 'Time'
          },
          vAxis: {
            title: 'Temperature'
          },
          colors: ['#A9D1DB'],
          width: 800

        };
        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

        chart.draw(data, options);
}
  htmlText += "</div>";

  divId.innerHTML = htmlText;
}


// Get detailed card weather information
function sendURL(latitude, longitude, time) {
  let xmlhttp = new XMLHttpRequest();
  xmlhttp.open("POST", "#", false);
  let formData = new FormData();
  formData.append("latitude", latitude);
  formData.append("longitude", longitude);
  formData.append("time", time);
  xmlhttp.send(formData);

  if (xmlhttp.status == 200) {
    let summaryElem = document.getElementById("currentSummary");
    let tableElem = document.getElementById('weeklyTableWrapper');
    document.getElementById("currentSummaryWrapperBox").removeChild(summaryElem);
    document.getElementById("currentSummaryWrapperBox").removeChild(tableElem);

    let jsonObj = JSON.parse(xmlhttp.responseText);
    return jsonObj;
  }
}


// Function called when one of summaries in weekly table is clicked
function getDetailedJSON(latitude, longitude, time) {
  let jsonObj = sendURL(latitude, longitude, time);
  populateDetailedCard(jsonObj);
}

var statesList = {
  "States":[
    {
      "Abbreviation":"AL",
      "State":"Alabama"
    },
    {
      "Abbreviation":"AK",
      "State":"Alaska"
    },
    {
      "Abbreviation":"AZ",
      "State":"Arizona"
    },
    {
      "Abbreviation":"AR",
      "State":"Arkansas"
    },
    {
      "Abbreviation":"CA",
      "State":"California"
    },
    {
      "Abbreviation":"CO",
      "State":"Colorado"
    },
    {
      "Abbreviation":"CT",
      "State":"Connecticut"
    },
    {
      "Abbreviation":"DE",
      "State":"Delaware"
    },
    {
      "Abbreviation":"DC",
      "State":"District Of Columbia"
    },
    {
      "Abbreviation":"FL",
      "State":"Florida"
    },
    {
      "Abbreviation":"GA",
      "State":"Georgia"
    },
    {
      "Abbreviation":"HI",
      "State":"Hawaii"
    },
    {
      "Abbreviation":"ID",
      "State":"Idaho"
    },
    {
      "Abbreviation":"IL",
      "State":"Illinois"
    },
    {
      "Abbreviation":"IN",
      "State":"Indiana"
    },
    {
      "Abbreviation":"IA",
      "State":"Iowa"
    },
    {
      "Abbreviation":"KS",
      "State":"Kansas"
    },
    {
      "Abbreviation":"KY",
      "State":"Kentucky"
    },
    {
      "Abbreviation":"LA",
      "State":"Louisiana"
    },
    {
      "Abbreviation":"ME",
      "State":"Maine"
    },
    {
      "Abbreviation":"MD",
      "State":"Maryland"
    },
    {
      "Abbreviation":"MA",
      "State":"Massachusetts"
    },
    {
      "Abbreviation":"MI",
      "State":"Michigan"
    },
    {
      "Abbreviation":"MN",
      "State":"Minnesota"
    },{
      "Abbreviation":"MS",
      "State":"Mississippi"
    },{
      "Abbreviation":"MO",
      "State":"Missouri"
    },{
      "Abbreviation":"MT",
      "State":"Montana"
    },{
      "Abbreviation":"NE",
      "State":"Nebraska"
    },{
      "Abbreviation":"NV",
      "State":"Nevada"
    },{
      "Abbreviation":"NH",
      "State":"New Hampshire"
    },{
      "Abbreviation":"NJ",
      "State":"New Jersey"
    },{
      "Abbreviation":"NM",
      "State":"New Mexico"
    },{
      "Abbreviation":"NY",
      "State":"New York"
    },{
      "Abbreviation":"NC",
      "State":"North Carolina"
    },{
      "Abbreviation":"ND",
      "State":"North Dakota"
    },{
      "Abbreviation":"OH",
      "State":"Ohio"
    },{
      "Abbreviation":"OK",
      "State":"Oklahoma"
    },{
      "Abbreviation":"OR",
      "State":"Oregon"
    },{
      "Abbreviation":"PA",
      "State":"Pennsylvania"
    },{
      "Abbreviation":"RI",
      "State":"Rhode Island"
    },{
      "Abbreviation":"SC",
      "State":"South Carolina"
    },{
      "Abbreviation":"SD",
      "State":"South Dakota"
    },{
      "Abbreviation":"TN",
      "State":"Tennessee"
    },{
      "Abbreviation":"TX",
      "State":"Texas"
    },{
      "Abbreviation":"UT",
      "State":"Utah"
    },{
      "Abbreviation":"VT",
      "State":"Vermont"
    },{
      "Abbreviation":"VA",
      "State":"Virginia"
    },{
      "Abbreviation":"WA",
      "State":"Washington"
    },{
      "Abbreviation":"WV",
      "State":"West Virginia"
    },{
      "Abbreviation":"WI",
      "State":"Wisconsin"
    },
    {
      "Abbreviation":"WY",
      "State":"Wyoming"
    }
  ]
};

// Populate states drop down menu with all States
var statesNode = document.getElementById('state');
for (let i = 0; i < statesList.States.length; i++) {
  let selectedOrNot = "";
  statesNode.insertAdjacentHTML('beforeend', "<option value="+ "'" + statesList.States[i].State + "'" + selectedOrNot + ">"
  + statesList.States[i].State + "</option>");
}
</script>
</html>
<?php
/* close php html page response */
}
?>
