import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ChartsModule } from 'ng2-charts';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WeatherComponent } from './weather/weather.component';
import { ApiService } from './api.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ResultsComponent } from './results/results.component';
import { CurrentTabComponent } from './current-tab/current-tab.component';
import { HourlyTabComponent } from './hourly-tab/hourly-tab.component';
import { WeeklyTabComponent } from './weekly-tab/weekly-tab.component';
//import { HourlyChartComponent } from './hourly-chart/hourly-chart.component';
import { WeeklyChartComponent } from './weekly-chart/weekly-chart.component';
import { FavoritesComponent } from './favorites/favorites.component';
import { FavoritesService } from './favorites.service';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    HttpModule,
    BrowserAnimationsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    NgbModule,
    ChartsModule,
  ],
  declarations: [
    AppComponent,
    WeatherComponent,
    ResultsComponent,
    CurrentTabComponent,
    HourlyTabComponent,
    WeeklyTabComponent,
    WeeklyChartComponent,
    FavoritesComponent
    
  ],
  providers: [ApiService, FavoritesService],
  bootstrap: [AppComponent]
})
export class AppModule { }