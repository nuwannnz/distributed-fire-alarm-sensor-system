package com.firealarm.services;

import com.firealarm.helpers.APIHelper;
import com.firealarm.helpers.JsonHelper;
import com.firealarm.services.Constants;
import firealarm.rmi.api.FireAlarmSensor;
import firealarm.rmi.api.FireAlarmSensorService;


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

    private List<FireAlarmSensor> sensorsList;

    public FireAlarmServiceImpl() throws RemoteException {
        this.sensorsList = new ArrayList<>();

        // schedule fire alarm fetching to run at every 5 seconds
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        this.fetchFireAlarmSensors();
    }

    private void fetchFireAlarmSensors(){
//        System.out.println("Fetching alarm sensors");
        StringBuffer res = null;
        try {
            res = APIHelper.get(FIRE_ALARM_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(res == null){
            return;
        }

        JsonArray jo =  JsonHelper.getJsonArrayFromString(res.toString());

        List<FireAlarmSensor> alarms = jo.stream().map(alarmJson -> {

            return JsonHelper.getFireAlamSensorFromJson((JsonObject) alarmJson);

        }).collect(Collectors.toList());

        // update the sensor list
        this.sensorsList.removeAll(this.sensorsList);
        this.sensorsList.addAll(alarms);
    }

    @Override
    public List<FireAlarmSensor> getAllFireAlarms() throws RemoteException {


        return this.sensorsList;
    }

    @Override
    public FireAlarmSensor getFireAlarm(int i) throws RemoteException {
        return null;
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
    public FireAlarmSensor updateFireAlarm(String token, int alarmId, String floor, String room) throws RemoteException {
        JsonObject updateFireAlarmParams = Json.createObjectBuilder()
                .add("id", alarmId)
                .add("floor", floor)
                .add("room", room)
                .build();

        StringBuffer res = null;

        try {
            res = APIHelper.patch(FIRE_ALARM_URL, updateFireAlarmParams, token);
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
            res = APIHelper.delete(FIRE_ALARM_URL, deleteFireAlarmParams, token);
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
}
