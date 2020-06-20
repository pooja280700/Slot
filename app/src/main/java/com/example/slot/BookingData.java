package com.example.slot;

public class BookingData {

    String name;
    String email;
    String work ;
    String Date;
    String Time;

    public BookingData(String name, String email, String work, String date, String time) {
        this.name = name;
        this.email = email;
        this.work = work;
        Date = date;
        Time = time;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getWork() {
        return work;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setTime(String time) {
        Time = time;
    }
}
