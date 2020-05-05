/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fire.alarm.desktopi.client;

import firealarm.rmi.api.FireAlarmSensor;
import firealarm.rmi.api.FireAlarmSensorService;
import firealarm.rmi.api.FireAlarmSensorWarningListener;
import firealarm.rmi.api.UserService;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
public class FireAlarmDesktopClient extends UnicastRemoteObject implements Serializable, FireAlarmSensorWarningListener {

    private FireAlarmSensorService fireAlarmService;    
    
    transient private SensorWindow mainWindow;
    
    public FireAlarmDesktopClient(FireAlarmSensorService fireAlarmSensorService, UserService userService) throws  RemoteException{
        this.fireAlarmService = fireAlarmSensorService;
        
        
          try{
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e){
            
        }
                
        mainWindow = new SensorWindow(fireAlarmSensorService, userService);
        
        registerListeners();
        
      
        
    }
        
  
    private void registerListeners(){
        try {
            System.out.println("Registering warning listener");
            fireAlarmService.registerWarningListener(this);
        } catch (RemoteException ex) {
            Logger.getLogger(FireAlarmDesktopClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); 
                try {
                    FireAlarmDesktopClient.this.fireAlarmService.removeWarningListner(FireAlarmDesktopClient.this);
                    System.out.println("Removed warning listener");
                } catch (RemoteException ex) {
                    Logger.getLogger(FireAlarmDesktopClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             
        });
    }


    @Override
    public void notifyWarning(FireAlarmSensor sensor) throws RemoteException {
           
        
        mainWindow.forceFetch();
        
        showNotification(sensor);
    }
    
    private void showNotification(FireAlarmSensor sensor){
                   
        SystemTray tray = SystemTray.getSystemTray();
       
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/fire/alarm/desktopi/client/assets/alarm_warning.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
       
        trayIcon.setImageAutoSize(true);
       
        trayIcon.setToolTip("Fire alarm WARNING");
        try {
            tray.add(trayIcon);
        } catch (AWTException ex) {
            Logger.getLogger(SensorWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        String msg = "Fire alarm in " + sensor.getRoom() + " room at " + sensor.getFloor() + " floor has a warning level"; 
        trayIcon.displayMessage("Fire alarm WARNING",   msg , TrayIcon.MessageType.WARNING);
        
    }
    
    public void displayWindow(){
        
        mainWindow.setVisible(true);
    }
    

}
