import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Papa from 'papaparse';

class App extends Component {

  processComplete(results) {
    //console.log("Complete data:", results.data);
    //console.log("Complete meta:", results.meta);
    //console.log("Complete errors:", results.errors);

    this.setState({"departureData": results.data});
  }
    
  processError(error, file) {
    console.log('error: ' + error + ', file: ' + file);
  }

  constructor() {
    super();
    this.processComplete = this.processComplete.bind(this);
    Papa.parse('http://52.71.172.146:3000/Departures.csv', {
        download: true,
        delimiter: "",	// auto-detect
        newline: "",	// auto-detect
        quoteChar: '"',
        header: true,
        complete: this.processComplete,
        error: this.processError,
        skipEmptyLines: true
    });
  }

  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to Mike's MBTA Departure Schedule React App</h2>
        </div>
        <DepartureBoard data={this.state ? this.state.departureData : ""}/>
      </div>
    );
  }
}


class DepartureBoard extends Component {

  render() {
    var rows = '';
    if(this.props.data) {
      rows = this.props.data.map(function(row) {
        var schedTime = new Date(row.ScheduledTime);
        return (
          <DepartureBoardRow key={row.Destination + row.ScheduledTime} destination={row.Destination} scheduledTime={new Date(row.ScheduledTime).toUTCString()} track={row.Track} status={row.Status}/>
        );
      });
    }

    return (
      <div id="departure-board">
        <DepartureBoardHeader/>
        {rows}
      </div>
     );
  }
}

class DepartureBoardHeader extends Component {

  render() {
    var days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

    var d = new Date();
    var day = days[d.getDay()];
    var hr = d.getHours();
    var min = d.getMinutes();
    if(min < 10) {
      min = "0" + min;
    }
    var ampm = hr < 12 ? "am" : "pm";
    var date = d.getDate();
    var month = d.getMonth() + 1;
    if(month < 10) {
      month = "0" + month;
    }
    var year = d.getFullYear();

    return (
      <div id="departure-board-header">
        <div id="left-side" className="yellow-text">
          <div id="left-top">{day}</div>
          <div id="left-bottom">{month}-{date}-{year}</div>
        </div>
        <div id="center">North/South Station Train Information</div>
        <div id="right-side" className="yellow-text">
          <div id="right-top">Current Time</div>
          <div id="right-bottom">{hr}:{min} {ampm}</div>
        </div>
        <div id="heading-row">
          <div id="destination-heading">Destination</div>
          <div id="scheduled-time-heading">Scheduled Time</div>
          <div id="track-heading">Track</div>
          <div id="status-heading">Status</div>
        </div>
      </div>
    );
  }
}

class DepartureBoardRow extends Component {
  
  render() {
    return (
      <div className="departure-board-row">
        <div className="departure-row-destination">{this.props.destination}</div>
        <div className="departure-row-scheduled-time">{this.props.scheduledTime}</div>
        <div className="departure-row-track">{this.props.track}</div>
        <div className="departure-row-status">{this.props.status}</div>
       </div>
    );
  }
}

export default App;
