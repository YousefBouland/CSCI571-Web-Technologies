import { Component, OnInit, Input } from '@angular/core';
import { WeatherObj } from '../weather-classes';

@Component({
  selector: 'app-current-tab',
  templateUrl: './current-tab.component.html',
  styleUrls: ['./current-tab.component.css']
})
export class CurrentTabComponent implements OnInit {

  @Input() city: string;
  @Input() weatherObj: WeatherObj;
  @Input() imageLink: string;


  constructor() { }

  ngOnInit() {
  }

}
