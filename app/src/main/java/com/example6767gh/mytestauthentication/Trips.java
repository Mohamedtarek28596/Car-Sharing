package com.example6767gh.mytestauthentication;



public class Trips {

    private String start;
    private String end;
    private String time;



    public Trips() {
    }


    public Trips(String start, String end, String time) {
        this.start = start;
        this.end = end;
        this.time = time;
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

}