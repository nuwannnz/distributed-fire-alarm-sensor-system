package firealarm.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface defines the API to interact
 * with the user services of the RMI server
 * @author Nuwan Karunarathna
 *
 */
public interface UserService extends Remote {
	boolean hasAdmin() throws RemoteException;
	boolean createAdmin(String email, String password) throws RemoteException;
	
	
	/**Login the user
	 * @param email Email of the user
	 * @param password Password of the user
	 * @return A token if the email and password are correct or null otherwise
	 */
	String	login(String email, String password) throws RemoteException;
}
