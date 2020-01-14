import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-weekly-chart',
  templateUrl: './weekly-chart.component.html',
  styleUrls: ['./weekly-chart.component.css']
})
export class WeeklyChartComponent implements OnInit {

  @Input() dailyData;
  @Input() ResultTimeZone: string;

  constructor() { }

  public barChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true
  }
  
  public barChartLabels;
  //public barChartLabels = [];
  public barChartType = 'horizontalBar';
  public barChartLegend = true;
  public barChartData;

  ngOnInit() {
    // Initialize x and y axis variables
    let xRangeData: number[][];
    xRangeData = [];
    for (let i = 0; i < 8; i++) {
      xRangeData[i] = [];
    }

    let yData: string[];
    yData = [];

    let timeZone = this.ResultTimeZone;
    let i = 0;

    // Assign values for each y data point (date) and each x data point (temperature range)
    this.dailyData.data.forEach(function (dayData) {
      xRangeData[i][0] = Math.floor(dayData.temperatureLow);
      xRangeData[i][1] = Math.floor(dayData.temperatureHigh);
      let s = new Date(dayData.time*1000); // Multiply by 1000 to get milliseconds
      let time = s.toLocaleDateString('en-US', {timeZone: timeZone, year: 'numeric', month:'2-digit', day:'2-digit'});
      yData[i] = time;
      i++;
    });

    this.barChartData = [
      {data: xRangeData,
         label: 'Day wise temperature range',
         backgroundColor: "#8dd2f6"}
    ];
    this.barChartLabels = yData;
    // console.table(xRangeData);
  }
}