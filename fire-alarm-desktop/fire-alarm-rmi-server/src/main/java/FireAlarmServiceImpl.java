import firealarm.rmi.api.FireAlarmSensor;
import firealarm.rmi.api.FireAlarmSensorService;


import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.*;


public class FireAlarmServiceImpl extends UnicastRemoteObject implements FireAlarmSensorService {
    private final static String FIRE_ALARM_URL = Constants.FIRE_ALARM_API_URL +  "/fire-alarm";

    protected FireAlarmServiceImpl() throws RemoteException {
    }

    @Override
    public List<FireAlarmSensor> getAllFireAlarms() throws RemoteException {
        StringBuffer res = null;
        try {
            res = APIHelper.get(FIRE_ALARM_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(res == null){
            return null;
        }

        JsonArray jo =  JsonHelper.getJsonArrayFromString(res.toString());

        List<FireAlarmSensor> alarms = jo.stream().map(alarmJson -> {

            return JsonHelper.getFireAlamSensorFromJson((JsonObject) alarmJson);

        }).collect(Collectors.toList());

        return alarms;
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
}
