/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fire.alarm.desktopi.client;

import firealarm.rmi.api.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 *
 * @author nuwan
 */
public class FireAlarmDesktopiClientStarter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);

            FireAlarmSensorService fireAlarmSensorService = (FireAlarmSensorService) registry.lookup(APIServiceNames.FIRE_ALARM_SERVICE.toString());
            UserService userService = (UserService) registry.lookup(APIServiceNames.USER_SERVICE.toString());

            System.out.println("Services loaded");

            List<FireAlarmSensor> sensorList = fireAlarmSensorService.getAllFireAlarms();

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    SensorWindow window = new SensorWindow(fireAlarmSensorService, userService);
                    window.setVisible(true);
                   
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

}
