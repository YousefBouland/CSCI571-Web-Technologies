import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-hourly-tab',
  templateUrl: './hourly-tab.component.html',
  styleUrls: ['./hourly-tab.component.css']
})
export class HourlyTabComponent implements OnInit {

  public menuSelection: string;
  @Input() hourlyData: number[][];
  private hourlyDataSelections = ['Temperature', 'Pressure', 'Humidity', 'Ozone', 'Visibility', 'Wind Speed'];
  private yLabels = ['Fahrenheit', 'Millibars', '% Humidity', 'Dobson Units', 'Miles (Maxiumum 10)', 'Miles per Hour' ];

  public barChartOptions;
  public barChartLabels;
  public barChartType;
  public barChartLegend;
  public barChartData;
  public selectionIndex;
    
  constructor() { 
    this.menuSelection = this.hourlyDataSelections[0];
  }

  ngOnInit() {
    this.barChartOptions = {
      scaleShowVerticalLines: false,
      responsive: true,
      scales: {
        xAxes: [{
          scaleLabel: {
            display: true,
            labelString: 'Time difference from current hour'
          }
        }],
        yAxes: [{
          scaleLabel: {
            display: true,
            labelString: this.yLabels[0]
          }
        }]
      }
    }

    this.barChartLabels = [];
    this.barChartType = 'bar';
    this.barChartLegend = true;

    for (let i = 0; i < 24; i++) {
      this.barChartLabels[i] = i;
    }

    console.log(this.menuSelection);
    this.selectionIndex = this.findSelectionIndex(this.menuSelection);

    console.log(this.hourlyData[this.selectionIndex]);
    this.barChartData = [
      { data: this.hourlyData[this.selectionIndex],
         label: this.menuSelection,
         backgroundColor: "#8dd2f6"}
    ];
  }

  onChange() {
    console.log(this.menuSelection);
    this.selectionIndex = this.findSelectionIndex(this.menuSelection);

    this.barChartOptions = {
      scaleShowVerticalLines: false,
      responsive: true,
      scales: {
        xAxes: [{
          scaleLabel: {
            display: true,
            labelString: 'Time difference from current hour'
          }
        }],
        yAxes: [{
          scaleLabel: {
            display: true,
            labelString: this.yLabels[this.selectionIndex]
          }
        }]
      }
    }

    console.log(this.hourlyData[this.selectionIndex]);
    this.barChartData = [
      { data: this.hourlyData[this.selectionIndex],
         label: this.menuSelection,
         backgroundColor: "#8dd2f6"}
    ];
  }

  //['temperature', 'pressure', 'humidity', 'ozone', 'visibility', 'windSpeed']
  findSelectionIndex(menuSelection: string): number {
    if (menuSelection === "Temperature") {
      return 0;
    } else if (menuSelection === "Pressure") {
      return 1;
    } else if (menuSelection === "Humidity") {
      return 2;
    } else if (menuSelection === "Ozone") {
      return 3;
    } else if (menuSelection === "Visibility") {
      return 4;
    } else if (menuSelection === "Wind Speed") {
      return 5;
    }
  }

}