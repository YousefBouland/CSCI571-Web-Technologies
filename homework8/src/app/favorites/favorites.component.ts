import { Component, OnInit } from '@angular/core';
import { FavoritesService } from '../favorites.service';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  favs;

  constructor(
    private favoritesService: FavoritesService
  ) {
    this.favs = this.favoritesService.getFavs();
   }

  ngOnInit() {
  }

}
