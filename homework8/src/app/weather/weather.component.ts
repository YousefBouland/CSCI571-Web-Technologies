import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators, RequiredValidator } from '@angular/forms';
import { Observable, empty } from 'rxjs';
import { map, startWith, switchMap, distinctUntilChanged, filter, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { states } from '../states';
import { InputAddressObj, CoordinatesAndCity, WeatherObj } from '../weather-classes';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})

export class WeatherComponent implements OnInit {
  form: FormGroup;
  states = states;
  filteredCities: string[] = [];

  constructor(
    private apiService: ApiService,
    private formBuilder: FormBuilder,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      street: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      currentLocation: false
    });


    // Make Api call on city input box for autocomplete on each character change
    this.form
      .get('city')
      .valueChanges
      .pipe(
        distinctUntilChanged(),
        filter(value => value ? true : false),
        switchMap((value: string) => this.apiService.search(value)
          .pipe(catchError(() => { return empty() }))
        )
      ).subscribe(cities => {
        if (cities == null) {
        } else {
          this.filteredCities = cities
        }
      }
      );
    this.onChanges();
  }


  // On check box value change, disable/enable the form
  onChanges(): void {
    this.form.get('currentLocation').valueChanges.subscribe(val => {
      if (val) {
        this.form.get('street').disable();
        this.form.get('city').disable();
        this.form.get('state').disable();
      } else {
        this.form.get('street').enable();
        this.form.get('city').enable();
        this.form.get('state').enable();
      }
    })
  }


    // If Current location checked, make call to ip-api, else make call to getCoordinates api. check if address is valid, then
    // make call to getWeatherForeacast api and return json
  onSubmit(InputItem) {
    if (InputItem.currentLocation == true) {
      //console.log(InputItem.currentLocation);
      return this.apiService.getCurrentLocation().subscribe(
        (response: CoordinatesAndCity) => {
          let lat = response.lat.toString();
          let lng = response.lon.toString();
          let city = response.city.toString()
          let state = response.region.toString(); 
          // console.log("inputForm value sent to getWeather lat: " + lat);
          // console.log("inputForm value sent to getWeather lng: " + lng);
          this.getWeather(lat, lng, city, state);
        }
      );
    } else {
      let addressObj: InputAddressObj = {
        street: InputItem.street,
        city: InputItem.city,
        state: InputItem.state,
      }
      return this.apiService.getAddressCoordinates(addressObj).subscribe(
        (response: CoordinatesAndCity) => {
          let lat = response.lat.toString();
          let lng = response.lng.toString();
          let city = addressObj.city;
          let state= addressObj.state;
          // console.log("inputForm value sent to getWeather lat: " + lat);
          // console.log("inputForm value sent to getWeather lng: " + lng);
          this.getWeather(lat, lng, city, state);
        }
      );
    }
  }


  // Pass location data to results component
  getWeather(lat: string, lng: string, city: string, state: string) {
    // console.log("getWeather: lat is: " + lat + ", lon is: " + lng + ", " + "city is: " + city + ", state is: " + state);
    this.router.navigate(['/results', { lat: lat, lng: lng, city: city, state: state }]);
  }


  // Function bound to displayWith property for city autocomplete
  displayFn(city: string) {
    if (city) { return city; }
  }


  // Form clear
  onClear() {
    this.form.reset();
    this.router.navigate(['/']);
    this.form.controls['city'].setValue(null);
    this.filteredCities = [];
  }
}