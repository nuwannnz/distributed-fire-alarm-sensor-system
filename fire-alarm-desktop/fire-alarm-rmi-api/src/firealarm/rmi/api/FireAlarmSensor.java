package firealarm.rmi.api;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class represents a Fire alarm sensor
 * 
 * @author Nuwan Karunarathna
 *
 */

public class FireAlarmSensor implements Serializable {

    private int id;
    private String floor;
    private String room;
    private int smokeLevel;
    private int co2Level;
    private LocalDate createdDate;
    private LocalDate updatedDate;



    public FireAlarmSensor(int id, String floor, String room, int smokeLevel, int co2Level, LocalDate createdDate,
                           LocalDate updatedDate) {
        super();
        this.id = id;
        this.floor = floor;
        this.room = room;
        this.smokeLevel = smokeLevel;
        this.co2Level = co2Level;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }


    public FireAlarmSensor(int id, String floor, String room, int smokeLevel, int co2Level) {
        super();
        this.id = id;
        this.floor = floor;
        this.room = room;
        this.smokeLevel = smokeLevel;
        this.co2Level = co2Level;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFloor() {
        return floor;
    }
    public void setFloor(String floor) {
        this.floor = floor;
    }
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    public int getSmokeLevel() {
        return smokeLevel;
    }
    public void setSmokeLevel(int smokeLevel) {
        this.smokeLevel = smokeLevel;
    }
    public int getCo2Level() {
        return co2Level;
    }
    public void setCo2Level(int co2Level) {
        this.co2Level = co2Level;
    }
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    public LocalDate getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

}