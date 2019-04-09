package com.example6767gh.mytestauthentication;



public class Trips {

    private String start;
    private String end;
    private String time;
    private String userID, carID;



    public Trips() {
    }


    public Trips(String start,String end, String time,String userID, String carID) {
        this.start = start;
        this.end = end;
        this.time = time;
        this.userID = userID;
        this.carID = carID;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }
}