/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fire.alarm.desktopi.client;

import firealarm.rmi.api.FireAlarmSensor;
import firealarm.rmi.api.FireAlarmSensorService;
import firealarm.rmi.api.UserService;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nuwan
 */
public class FireAlarmDesktopClient implements Serializable, Runnable {

    private FireAlarmSensorService fireAlarmService;
    private UserService userService;

    private boolean hasAdmin = false;
    private String userAuthToken = null;
    private List<FireAlarmSensor> sensorList;
    
    private SensorWindow mainWindow;
    
    public FireAlarmDesktopClient(FireAlarmSensorService fireAlarmSensorService, UserService userService) {
        this.fireAlarmService = fireAlarmSensorService;
        this.userService = userService;
        
//        mainWindow = new SensorWindow(, userService)
        
         // schedule fetching of sensors to run every 30 seconds
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this, 0, 5, TimeUnit.SECONDS);
    }

   /**
     * This method will fetch the fire alarms from the RMI server and add them
     * to the sensor list
     */
    private void fetchFireAlarms() {
        System.out.println("fetching fire alarms");
        try {
            List<FireAlarmSensor> sensors = fireAlarmService.getAllFireAlarms();

            sensorList.removeAll(sensorList);
            sensorList.addAll(sensors);

//            populateSensorPanel();

        } catch (RemoteException ex) {
            Logger.getLogger(SensorWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        fetchFireAlarms();
    }
    
    
    

}
