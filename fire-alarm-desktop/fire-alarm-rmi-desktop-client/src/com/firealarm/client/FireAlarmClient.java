package com.firealarm.client;

import firealarm.rmi.api.APIServiceNames;
import firealarm.rmi.api.FireAlarmSensor;
import firealarm.rmi.api.FireAlarmSensorService;
import firealarm.rmi.api.UserService;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FireAlarmClient implements Runnable {

    private FireAlarmSensorService fireAlarmSensorService;
    private UserService userService;

    // forms, windows
    private FireAlarmSensorWindow fireAlarmSensorWindow;
    private FireAlarmSensorForm fireAlarmSensorForm;

    public FireAlarmClient(){
      this.loadServices();

      this.fireAlarmSensorWindow = new FireAlarmSensorWindow();
      this.fireAlarmSensorForm = new FireAlarmSensorForm();

      setActionListeners();

      // refresh sensor list every 30 seconds
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this, 0, 30, TimeUnit.SECONDS);
    }

    private void loadServices(){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);

            this.fireAlarmSensorService = (FireAlarmSensorService) registry.lookup(APIServiceNames.FIRE_ALARM_SERVICE.toString());
            this.userService = (UserService) registry.lookup(APIServiceNames.USER_SERVICE.toString());

            System.out.println("Services loaded");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void setActionListeners(){
        this.fireAlarmSensorWindow.setLoginBtnClickListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FireAlarmClient.this.handleOpenLoginBtnCLick();
            }
        });
    }

    private void refreshFireAlarms(){
        try {
            List<FireAlarmSensor> alarmList = this.fireAlarmSensorService.getAllFireAlarms();
            this.fireAlarmSensorWindow.setFireAlarmList(alarmList);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void startClient(){
      // display fire alarm sensor window
      this.fireAlarmSensorWindow.setVisible(true);

    }

    private void handleOpenLoginBtnCLick(){
        this.fireAlarmSensorForm.setVisible(true);
    }

    @Override
    public void run() {
        this.refreshFireAlarms();
    }
}
