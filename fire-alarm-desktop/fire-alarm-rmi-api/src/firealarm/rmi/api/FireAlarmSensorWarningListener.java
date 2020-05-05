/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firealarm.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author nuwan
 */
public interface FireAlarmSensorWarningListener extends Remote{
    void notifyWarning(FireAlarmSensor sensor) throws RemoteException;
}
