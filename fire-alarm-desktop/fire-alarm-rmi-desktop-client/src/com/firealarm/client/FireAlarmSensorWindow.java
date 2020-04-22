package com.firealarm.client;

import firealarm.rmi.api.FireAlarmSensor;

import javax.swing.*;
import java.util.List;

public class FireAlarmSensorWindow extends JFrame {
    private JButton loginBtn;
    private JPanel panel1;

    public FireAlarmSensorWindow(){
        // add the panel
        add(panel1);

        // set title
        setTitle("Fire Alarm sensors");

        // set size
        setSize(400, 500);
    }

    public void setLoginBtnClickListener(Action clickListener){
        this.loginBtn.addActionListener(clickListener);

    }

    public void setFireAlarmList(List<FireAlarmSensor> alarmList){
        // display alarm list
    }
}
