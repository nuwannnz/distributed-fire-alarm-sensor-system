package firealarm.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface defines the API to interact
 * with the fire alarm service of the RMI server
 * @author Nuwan Karunarathna
 *
 */
public interface FireAlarmSensorService extends Remote {
	List<FireAlarmSensor> getAllFireAlarms() throws RemoteException;

	FireAlarmSensor getFireAlarm(int id) throws RemoteException;

	FireAlarmSensor createFireAlarm(String token, String floor, String room) throws RemoteException;

	FireAlarmSensor updateFireAlarm(String token, int id, String floor, String room) throws RemoteException;

	boolean deleteFireAlarm(String token, int id) throws RemoteException;

}
