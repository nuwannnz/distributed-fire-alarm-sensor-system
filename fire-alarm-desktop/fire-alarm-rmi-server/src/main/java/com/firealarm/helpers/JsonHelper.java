package com.firealarm.helpers;

import firealarm.rmi.api.FireAlarmSensor;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class JsonHelper {

    /**
     * Convert a string to a JSON object
     * @param str
     * @return
     */
    public static JsonObject getJsonObjectFromString(String str){

        JsonReader reader = Json.createReader(new StringReader(str));

        JsonObject parsedObject =  reader.readObject();
        reader.close();
        return parsedObject;
    }

    /**
     * Conver a string to a JSON array
     * @param str
     * @return
     */
    public static JsonArray getJsonArrayFromString(String str){

        JsonReader reader = Json.createReader(new StringReader(str));

        JsonArray parsedArray =  reader.readArray();
        reader.close();
        return parsedArray;
    }

    /**
     * Convert a JSON object to a string
     * @param obj
     * @return
     */
    public static String parseJsonToString(JsonObject obj){
        String parsedString;
        Writer writer = new StringWriter();
        Json.createWriter(writer).write(obj);
        parsedString = writer.toString();
        return parsedString;
    }

    /**
     * Convert a JSON object to a FireAlarmSensor
     * @param alarmJson
     * @return
     */
    public static FireAlarmSensor getFireAlamSensorFromJson(JsonObject alarmJson){
        int id = ((JsonObject)alarmJson).getInt("id");
        String floor = ((JsonObject)alarmJson).getString("floor");
        String room = ((JsonObject)alarmJson).getString("room");
        int smokeLevel =  ((JsonObject)alarmJson).getInt("smoke_level");
        int co2Level =  ((JsonObject)alarmJson).getInt("co2_level");
        boolean isActive = ((JsonObject)alarmJson).getBoolean("isActive");


        FireAlarmSensor alarm = new FireAlarmSensor(
                id,
                floor,
                room,
                smokeLevel,
                co2Level,
                isActive
                );

        return alarm;
    }
}
