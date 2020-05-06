import React, { Component } from 'react'
import axios from 'axios';

import '../App.css';
import Header from './header.component';

export default class Alarms extends Component {

    constructor(props) {
        super(props);
        this.state = { alarms: [] };
    }

    //fetch get request every 40 seconds
    componentDidMount() {
        this.fetchAlarms();
        setInterval(() => {
            this.fetchAlarms();
        }, 40000);
    }

    fetchAlarms() {
        axios.get('http://localhost:5000/fire-alarms').then(response => {
            this.setState({ alarms: response.data })
        }).catch(function (error) {
            console.log(error);
        });
    }

    //check whether smoke and co2 levels are increased
    ringingAlarm(smoke, co2) {
        if (smoke > 5 && co2 > 5) {
            return "smokeandco2";
        } else if (smoke > 5) {
            return "smoke";
        } else if (co2 > 5) {
            return "co2";
        } else {
            return "inside";
        }
    }

    seeLevels(level) {
        if (level > 5) {
            return "red";
        }
    }

    progressBarChanger(level) {
        if (level > 5) {
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
                    {this.state.alarms.map(alarm => (
                        <div class="col-md-3 col-sm-5">
                            <div class="single-content" style={{ borderColor: alarm.isActive ? '#4CAF50' : 'lightgray' }}>
                                <div class={this.ringingAlarm(alarm.smoke_level, alarm.co2_level)}>
                                    <img src={require('../assets/alarm.png')} alt="alarm" /><hr />
                                    <h3 style={{ textAlign: 'center' }}>Room {alarm.room} on floor {alarm.floor} </h3>
                                    <h4>smoke Level</h4>
                                    <div class="progress">
                                        <div class={this.progressBarChanger(alarm.smoke_level)} role="progressbar" style={{ width: alarm.smoke_level + '0%' }} aria-valuenow={alarm.smoke_level} aria-valuemin="0" aria-valuemax="10">{alarm.smoke_level}</div>
                                    </div>
                                    <h4>Co2 Level</h4>
                                    <div class="progress">
                                        <div class={this.progressBarChanger(alarm.co2_level)} role="progressbar" style={{ width: alarm.co2_level + '0%' }} aria-valuenow={alarm.co2_level} aria-valuemin="0" aria-valuemax="10">{alarm.co2_level}</div>
                                    </div>

                                </div>
                                <div class="text-content"><br />
                                    <h4>Alarm ID : {alarm.id}</h4>
                                    <h5>Alarm Floor : {alarm.floor}</h5>
                                    <h5>Alarm Room : {alarm.room}</h5>
                                    <h5>Is active : {alarm.isActive}</h5>
                                    <h5 style={{ backgroundColor: this.seeLevels(alarm.smoke_level) }}>Alarm Smoke Level : {alarm.smoke_level}</h5>
                                    <h5 style={{ backgroundColor: this.seeLevels(alarm.co2_level) }}>Alarm Co2 level : {alarm.co2_level}</h5>

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
