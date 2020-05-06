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
            // locate the registry
            Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);

            // load the services
            FireAlarmSensorService fireAlarmSensorService = (FireAlarmSensorService) registry.lookup(APIServiceNames.FIRE_ALARM_SERVICE.toString());
            UserService userService = (UserService) registry.lookup(APIServiceNames.USER_SERVICE.toString());

            System.out.println("Services loaded");

            // start the client
            FireAlarmDesktopClient client = new FireAlarmDesktopClient(fireAlarmSensorService, userService);
            client.displayWindow();

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

}
