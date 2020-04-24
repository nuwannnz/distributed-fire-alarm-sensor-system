import React, { Component } from 'react'

export default class Header extends Component {
    render() {
        return (
            <div>
                <h1 style={{textAlign: 'center',textDecoration:'bold'}}> Fire Alarm Viewer</h1>
                <hr></hr>
                <p style={{fontSize: 30}}>
                    This application will display all the fire alarms that available.<br></br><br />
                    <hr />
                    Below fire alarms will blink in different colors according to the fire alarm status.<br />
                    <ul class="list-group"> <br />
                        <li class="list-group-item">If fire alarm blinks in <span style={{color:'orange', fontStyle:'italic'}}>orange</span> color, that means Smoke level is <span style={{textDecoration: 'underline'}}> high. </span></li><br />
                    
                        <li class="list-group-item">If fire alarm blinks in <span style={{color:'#333', fontStyle:'italic'}}>ash</span> color, that means Co2 level is <span style={{textDecoration: 'underline'}}>high. </span></li><br />
                    
                        <li class="list-group-item"> If fire alarm blinks in <span style={{color:'red', fontStyle:'italic'}}>red</span> color, that means Smoke and co2 levels both are <span style={{textDecoration: 'underline'}}>high. </span></li><br />
                    </ul>

                You can see additional information about the fire alrms by hover it.
                
                </p>
            </div>
        )
    }
}
