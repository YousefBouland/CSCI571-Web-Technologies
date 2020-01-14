// ------ Input Classes ------
export class InputAddressObj {
    street: string;
    city: string;
    state: string;
}

export class CoordinatesAndCity {
    lat: number;
    lng: number;
    lon: number;
    city: string;
    region: string;
}

// ------ Output Classes ------
export class WeatherObj {
    //city: string = '';     no need to send to back end in the 1st place
    timezone: string;
    currently: DataItem;
    daily: DataArray;
    hourly: DataArray;
}

export class StateSealObj {
    stateSealLink: string;
}

// WeatherObj related class
export class DataArray {
    data: DataItem[];
}

// WeatherObj related class
export class DataItem {
    // For current tab/weather card
    time: number;
    icon: string;
    summary: string;
    temperatureHigh: number;
    temperatureLow: number;
    
    // For hourly/weekly graphs
    temperature: number; // weather card and graph
    pressure: number;
    humidity: number;
    ozone: number;
    visibility: number;
    windSpeed: number; // weather card and graph
}

export class Favorite {
    id: number;
    imageLink: string;
    city: string;
    state: string;
    lat: number;
    lng: number;
}