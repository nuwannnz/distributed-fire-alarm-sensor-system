import firealarm.rmi.api.FireAlarmSensor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // create the registry
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            // create services
            FireAlarmServiceImpl fireAlarmService = new FireAlarmServiceImpl();
            UserServiceImpl userService = new UserServiceImpl();

            // bind the services
            registry.rebind("fire-alarm-Service",fireAlarmService);
            registry.rebind("user-service", userService);



            System.out.println("Services started");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
