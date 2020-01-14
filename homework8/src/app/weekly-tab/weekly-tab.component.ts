import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-weekly-tab',
  templateUrl: './weekly-tab.component.html',
  styleUrls: ['./weekly-tab.component.css']
})
export class WeeklyTabComponent implements OnInit {

  @Input() dailyData;
  @Input() ResultTimeZone: string;

  constructor() { }

  ngOnInit() {
    console.log(this.dailyData);
    console.log("TIMEZONE in weekly tab component: " + this.ResultTimeZone);
  }
}