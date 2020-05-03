package com.firealarm.server;

import com.firealarm.helpers.APIHelper;
import com.firealarm.services.FireAlarmServiceImpl;
import com.firealarm.services.UserServiceImpl;
import firealarm.rmi.api.APIServiceNames;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
//        System.setProperty("java.security.policy", "file:allowall.policy");
        try {
            // create the registry
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            // create services
            FireAlarmServiceImpl fireAlarmService = new FireAlarmServiceImpl();
            UserServiceImpl userService = new UserServiceImpl();

            // bind the services
            registry.rebind(APIServiceNames.FIRE_ALARM_SERVICE.toString(),fireAlarmService);
            registry.rebind(APIServiceNames.USER_SERVICE.toString(), userService);

//            APIHelper.allowMethods("PATCH");


            System.out.println("Services started");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
