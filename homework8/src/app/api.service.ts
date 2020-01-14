import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import { Observable } from 'rxjs';
import { InputAddressObj, WeatherObj, CoordinatesAndCity, StateSealObj } from './weather-classes';

const API_URL = environment.apiUrl;
const AP_API_URL = 'http://ip-api.com/json';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private http: HttpClient,
  ) {
  }

    // API: GET /autoComplete
    public search(input: string): Observable <string[]> {
      console.log("In apiService input is: " + input);
      const options = {
        params: {
          input: input
        }
      }
      return this.http.get<string[]>(API_URL+'/autoComplete', options);
    }


    // API: GET /addressCoordinates
    public getAddressCoordinates(addressObj: InputAddressObj): Observable<CoordinatesAndCity> {
      const options = {
        params: {
          street: addressObj.street,
          city: addressObj.city,
          state: addressObj.state
        }
      }
      return this.http.get<CoordinatesAndCity>(API_URL+'/addressCoordinates', options);
    }


    // API: GET /getCurrentLocation
    public getCurrentLocation(): Observable<CoordinatesAndCity> {
      return this.http.get<CoordinatesAndCity>(AP_API_URL);
    }
    

    // API: GET /getCurrentWeather
    public getCurrentWeather(coordinates: CoordinatesAndCity): Observable<WeatherObj> {
      // console.log('apiService lat:' + coordinates.lat.toString());
      // console.log('apiService lng:' + coordinates.lng.toString());
      const options = {
        params: {
          lat: coordinates.lat.toString(),
          lng: coordinates.lng.toString()
        }
      }
       return this.http.get<WeatherObj>(API_URL+'/getCurrentWeather', options);
     }


     // API: GET /getStateSeal
     public getStateSeal(stateName: string): Observable<StateSealObj> {
       const options = {
         params: {
           state: stateName
         }
       }
       return this.http.get<StateSealObj>(API_URL+'/getStateSeal', options);
     }
}