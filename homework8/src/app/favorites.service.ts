import { Injectable } from '@angular/core';
import { Favorite, CoordinatesAndCity, StateSealObj } from './weather-classes';


@Injectable({
  providedIn: 'root'
})
export class FavoritesService {
  private nextId: number;

  constructor() {
    let favs = this.getFavs();

    // if no favs, nextId is 0,
    // otherwise set to 1 more than last fav id
    if (favs.length == 0) {
      this.nextId = 0;
    } else {
      let maxId = favs[favs.length - 1].id;
      this.nextId = maxId + 1;
    }
  }
  
  public addFav(coordinatesAndCity: CoordinatesAndCity, imageLink: string) {
    let fav: Favorite = {
      id: this.nextId,
      imageLink: imageLink,
      city: coordinatesAndCity.city,
      state: coordinatesAndCity.region,
      lat: coordinatesAndCity.lat,
      lng: coordinatesAndCity.lng,
    };
    let favs = this.getFavs();
    favs.push(fav);

    //save the favs to local storage
    this.setLocalStorageFavs(favs);
    this.nextId++;
  }


  public getFavs(): Favorite[] {
    let localStorageItem = JSON.parse(localStorage.getItem('favs'));
    return localStorageItem == null ? [] : localStorageItem.favs;
  }


  public removeFav(city: string, state: string): void {
    let favs = this.getFavs();
    favs = favs.filter((fav) => fav.city != city && fav.state != state);
    this.setLocalStorageFavs(favs);
  }


  // private function to help save to local storage
  private setLocalStorageFavs(favs: Favorite[]): void {
    localStorage.setItem('favs', JSON.stringify({ favs: favs }));
  }
}