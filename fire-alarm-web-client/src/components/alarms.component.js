import React, { Component } from 'react'
import axios from 'axios';

import '../App.css';
import Header from './header.component';

export default class Alarms extends Component {

    constructor(props) {
        super(props);
        this.state = {alarms: []};
    }

    //fetch get request
    componentDidMount() {
        axios.get('https://fire-alarms.herokuapp.com/fire-alarm/').then(response => {
            response.data.push( 
                {  
                   "id": 5,
                      "floor": "4th",
                      "room": "102C",
                      "smoke_level": 6,
                      "co2_level": 7,
                      "createdAt": "2020-04-22T06:50:00.293Z",
                      "updatedAt": "2020-04-22T08:01:42.307Z"
                }
              )
            this.setState({alarms: response.data}) 
        }).catch(function(error) {
            console.log(error);
        });

    }
   
    //check whether smoke and co2 levels are increased
    ringingAlarm(smoke,co2) {
        
        if(smoke > 5 && co2 > 5) {
            return "smokeandco2";
        }else if(smoke > 5) {
            return "smoke";
        } else if(co2 > 5) {
            return "co2";
        }else {
            return "inside";
        }
    }

    seeLevels(level) {
        if(level > 5) {
            return "red";
        }
    }

    progressBarChanger(level) {
        if(level > 5) {
            return "progress-bar bg-danger";
        } else {
            return "progress-bar bg-success";
        }
    }

    render() {
        return (
            <div>
               <Header />
               <hr />
            <div class="row">
                {this.state.alarms.map(alarms => (
                    <div class="col-md-3 col-sm-5">
                    <div class="single-content">
                    <div class={this.ringingAlarm(alarms.smoke_level, alarms.co2_level)}>
                    <img src={require('../assets/alarm.png')} alt="alarm"/><hr />
                    <h3 style={{textAlign: 'center'}}>Room {alarms.room} on floor {alarms.floor} </h3>
                    <h4>smoke Level</h4> 
                    <div class="progress">
                        <div class={this.progressBarChanger(alarms.smoke_level)} role="progressbar" style={{width: alarms.smoke_level+'0%'}} aria-valuenow={alarms.smoke_level} aria-valuemin="0" aria-valuemax="10">{alarms.smoke_level}0%</div>
                    </div> 
                    <h4>Co2 Level</h4> 
                    <div class="progress">
                        <div  class={this.progressBarChanger(alarms.co2_level)} role="progressbar" style={{width: alarms.co2_level+'0%'}} aria-valuenow={alarms.co2_level} aria-valuemin="0" aria-valuemax="10">{alarms.co2_level}0%</div>
                    </div> 
                    
                    </div>
                        <div class="text-content"><br />
                            <h4>Alarm ID : {alarms.id}</h4>
                            <h5>Alarm Floor : {alarms.floor}</h5>
                            <h5>Alarm Room : {alarms.room}</h5>
                            <h5 style={{backgroundColor: this.seeLevels(alarms.smoke_level)}}>Alarm Smoke Level : {alarms.smoke_level}0%</h5>
                            <h5 style={{backgroundColor: this.seeLevels(alarms.co2_level)}}>Alarm Co2 level : {alarms.co2_level}0%</h5>
                            <h5>Alarm Created At</h5>
                            <h5>{alarms.createdAt}</h5>
                            <h5>Alarm Last Updated At</h5>
                            <h5>{alarms.updatedAt}</h5>
                            
                        </div>
                    </div>
                    <hr></hr>
                </div>
                ))}
            </div>

  
              
            </div>
        )
    }
}
