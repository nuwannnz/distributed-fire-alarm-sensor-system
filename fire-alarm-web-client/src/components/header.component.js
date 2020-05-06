import React, { Component } from 'react'

export default class Header extends Component {
    render() {
        return (
            <div>
                <h1 style={{ textAlign: 'center', textDecoration: 'bold' }}> Fire Alarm Viewer</h1>
                <hr></hr>
                <p style={{ fontSize: 30 }}>
                    This application will display all the fire alarms that available.<br></br><br />
                    <hr />
                    <ul class="list-group"> <br />
                        <li class="list-group-item"> If fire alarm blinks in <span style={{ color: 'red', fontStyle: 'italic' }}>red</span> color, that means Smoke or/and co2 levels are <span style={{ textDecoration: 'underline' }}>high. </span></li><br />
                    </ul>

                You can see additional information about the fire alrms by hover it.

                </p>
            </div>
        )
    }
}
