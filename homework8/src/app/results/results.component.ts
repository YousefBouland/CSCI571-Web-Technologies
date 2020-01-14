import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../api.service';
import { WeatherObj, CoordinatesAndCity } from '../weather-classes';
import { FavoritesService } from '../favorites.service';
import {NgbProgressbarConfig} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})

export class ResultsComponent implements OnInit {
  city: string;
  weatherObj: WeatherObj;
  public hourlyData: number[][];
  public dailyData;
  public ResultTimeZone: string;
  public coordinatesAndCity: CoordinatesAndCity;
  public imageLink: string;
  public starValue;
  public loadedYet;
  

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    public favoritesService: FavoritesService,
    public config: NgbProgressbarConfig,
  ) {
    config.max = 1000;
    config.striped = true;
    config.animated = true;
    config.type = 'success';
    config.height = '20px';
    
    this.city = "";

    //['Temperature', 'Pressure', 'Humidity', 'Ozone', 'Visibility', 'Wind Speed']
    this.hourlyData = [];
    for (let i = 0; i < 6; i++) {
      this.hourlyData[i] = [];
    }
  }

  ngOnInit(): void {
    // Get the route paramters for the location
    this.route.queryParams.subscribe(params => {
      const lat = this.route.snapshot.params.lat;
      const lng = this.route.snapshot.params.lng;
      this.city = this.route.snapshot.params.city;
      const state = this.route.snapshot.params.state;

      this.coordinatesAndCity = {
        lat: parseFloat(lat),
        lng: parseFloat(lng),
        lon: parseFloat(lng),
        city: this.city,
        region: state,
      }
      // console.log("Params reached results component: lat: " + lat + ", lng: " + lng + "city: " + this.city);

      // Call getData to populate the weather object instance variable and the string instance variable for the State's image link
      this.getData(this.coordinatesAndCity, state);
      let favs = this.favoritesService.getFavs();
      favs = favs.filter((fav) => fav.city == this.coordinatesAndCity.city && fav.state == this.coordinatesAndCity.region);
      // Depending on if the city and state is added to favorites change the shape of the star logo
      if (favs.length > 0) {
        this.starValue = 'star';
      } else {
        this.starValue = 'star_border';
      }
      this.loadedYet = false;
      
    })
  }
 
  // Get weather data and seal link
  // Uses API calls to get the weather data and seal link and stores them in a weather object instance variable
  // and the link for the seal image in a string instance variable
  getData(coordinates: CoordinatesAndCity, state: string) {
    this.apiService.getCurrentWeather(coordinates).subscribe(
      (response: WeatherObj) => {
        this.loadedYet = true;
        this.weatherObj = response;
        console.log(this.weatherObj.hourly);
        console.log(response);
        console.log("TIMEZONE is : " + this.weatherObj.timezone);
        this.populateHourlyArray();
        this.dailyData = this.weatherObj.daily;
        this.ResultTimeZone = this.weatherObj.timezone;
        this.apiService.getStateSeal(state).subscribe((stateSealLink) => {
          this.imageLink = stateSealLink.stateSealLink;
          console.log(stateSealLink.stateSealLink);
        });
      }
    );
  }

  //                     ===============> 24 days values
  /*row 0: ['temperature',
    row 1: 'pressure',
    row 2: 'humidity',
    row 3: 'ozone',
    row 4: 'visibility',
    row 5: 'windSpeed']
  */
  populateHourlyArray() {
    let menuOptions = ['temperature', 'pressure', 'humidity', 'ozone', 'visibility', 'windSpeed'];
    for (let i = 0; i < 6; i++) {
      //this.hourlyData[i][j] = this.weatherObj.hourly.data[j]
      this.populateRow(i, menuOptions[i]);
      }
      console.table(this.hourlyData);
  }

  populateRow(rowIndex: number, menuSelection: string) {
    for (let i = 0; i < 24; i++) {
      this.hourlyData[rowIndex][i] = this.weatherObj.hourly.data[i][menuSelection];
    }
  }

  toggleFavorite() {
    // If not currently in favorites
    if (this.starValue === 'star_border') {
      this.starValue = 'star';
      this.favoritesService.addFav(this.coordinatesAndCity, this.imageLink);
    } else { // If is part of favorites
      this.starValue = 'star_border';
      this.favoritesService.removeFav(this.coordinatesAndCity.city, this.coordinatesAndCity.region);
    }
  }
  
  //toggleFavorite()
  //star_border
  //this.favoritesService.addFav(coordinatesAndCity, imageLink)
}