package com.firealarm.services;

import com.firealarm.helpers.APIHelper;
import com.firealarm.helpers.JsonHelper;
import firealarm.rmi.api.FireAlarmSensor;
import firealarm.rmi.api.FireAlarmSensorService;
import firealarm.rmi.api.FireAlarmSensorWarningListener;


import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.json.*;


public class FireAlarmServiceImpl extends UnicastRemoteObject implements FireAlarmSensorService, Runnable {
    private final static String FIRE_ALARM_URL = Constants.FIRE_ALARM_API_URL +  "/fire-alarm";

    private final List<FireAlarmSensor> sensorsList;
    private final List<FireAlarmSensorWarningListener> warningListeners;

    public FireAlarmServiceImpl() throws RemoteException {
        this.sensorsList = new ArrayList<>();
        this.warningListeners = new ArrayList<>();

        // schedule fire alarm fetching to run at every 15 seconds
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this, 0, 15, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        this.fetchFireAlarmSensors();
    }

    private void fetchFireAlarmSensors(){
        StringBuffer res = null;
        try {
            res = APIHelper.get(FIRE_ALARM_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(res == null){
            return;
        }

        // parse the response
        JsonArray jo =  JsonHelper.getJsonArrayFromString(res.toString());

        // get fire alarm sensor objects from json
        List<FireAlarmSensor> alarms = jo.stream().map(alarmJson -> {

            return JsonHelper.getFireAlamSensorFromJson((JsonObject) alarmJson);

        }).collect(Collectors.toList());

        // update the sensor list
        this.sensorsList.removeAll(this.sensorsList);
        this.sensorsList.addAll(alarms);
    }

    private void checkSensorLevels(){
        for (FireAlarmSensor sensor :
                sensorsList) {
            if(sensor.getCo2Level() > 5 || sensor.getSmokeLevel() > 5){
                // issue a warning immediately
                notifyWarningListeners(sensor);

                // tell the REST API to send notifications


            }
        }
    }

    private void notifyWarningListeners(FireAlarmSensor sensor){
        for (FireAlarmSensorWarningListener listner :
                warningListeners) {
            try {
                listner.notifyWarning(sensor);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            notifyUsers(sensor);
        }
    }

    private void notifyUsers(FireAlarmSensor sensor){

        StringBuilder msgBuilder = new StringBuilder();

        if(sensor.getSmokeLevel() > 5 && sensor.getCo2Level() > 5){
            msgBuilder.append("Smoke level and the CO2 level");
        }else if(sensor.getSmokeLevel() > 5){
            msgBuilder.append("Smoke level");
        }else if( sensor.getCo2Level() > 5){
            msgBuilder.append("CO2 level");
        }

        msgBuilder.append(
                " of the fire alarm sensor in "
                        + sensor.getFloor()
                        + " floor "
                        + sensor.getRoom()
                        + " room ");

        String msg = msgBuilder.toString();
        JsonObject sendNotificationParams = Json.createObjectBuilder()
                .add("message", msg)
                .build();
        try {
            APIHelper.post(
                    FIRE_ALARM_URL + "/" + sensor.getId() + "/notify",
                    sendNotificationParams,
                    null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FireAlarmSensor> getAllFireAlarms() throws RemoteException {
        return this.sensorsList;
    }

    @Override
    public FireAlarmSensor getFireAlarm(int id) throws RemoteException {
        FireAlarmSensor sensorById = null;
        for (FireAlarmSensor sensor :
                sensorsList) {
            if(sensor.getId() == id){
                sensorById = sensor;
                break;
            }
        }
        return sensorById;
    }

    @Override
    public FireAlarmSensor createFireAlarm(String token, String floor, String room) throws RemoteException {
        JsonObject createFireAlamParms = Json.createObjectBuilder()
                .add("floor", floor)
                .add("room", room)
                .build();

        StringBuffer res = null;

        try {
            res = APIHelper.post(FIRE_ALARM_URL, createFireAlamParms, token);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(res == null){
            return null;
        }
        JsonObject alarmJson = JsonHelper.getJsonObjectFromString(res.toString());

        FireAlarmSensor sensor = JsonHelper.getFireAlamSensorFromJson(alarmJson);

        return sensor;
    }


    @Override
    public FireAlarmSensor updateFireAlarm(String token, FireAlarmSensor sensorTOUpdate) throws RemoteException {
        JsonObject updateFireAlarmParams = Json.createObjectBuilder()
                .add("floor", sensorTOUpdate.getFloor())
                .add("room", sensorTOUpdate.getRoom())
                .add("is_active", sensorTOUpdate.getIsActive())
                .add("smoke_level", sensorTOUpdate.getSmokeLevel())
                .add("co2_level", sensorTOUpdate.getCo2Level())
                .build();

        StringBuffer res = null;

        try {
            res = APIHelper.put(FIRE_ALARM_URL + "/" + sensorTOUpdate.getId(), updateFireAlarmParams, token);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(res == null){
            return null;
        }

        JsonObject alarmJson = JsonHelper.getJsonObjectFromString(res.toString());

        FireAlarmSensor updatedAlarm = JsonHelper.getFireAlamSensorFromJson(alarmJson);

        return updatedAlarm;
    }

    @Override
    public boolean deleteFireAlarm(String token, int alarmId) throws RemoteException {
        JsonObject deleteFireAlarmParams = Json.createObjectBuilder()
                .add("id", alarmId)
                .build();

        StringBuffer res = null;

        try {
            res = APIHelper.delete(FIRE_ALARM_URL + '/' + alarmId, deleteFireAlarmParams, token);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(res == null){
            return false;
        }

        JsonObject deleteResult = JsonHelper.getJsonObjectFromString(res.toString());

        try {
            boolean isDeleted = deleteResult.getBoolean("deleted");
            return isDeleted;
        }catch (NullPointerException e){
            return false;
        }

    }

    @Override
    public void sendAlarmEmail(int i) throws RemoteException {

    }

    public void registerWarningListener(FireAlarmSensorWarningListener listener) throws RemoteException {
        this.warningListeners.add(listener);
    }

    public void removeWarningListner(FireAlarmSensorWarningListener listener) throws RemoteException {
        this.warningListeners.remove(listener);
    }
}
