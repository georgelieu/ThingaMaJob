package edu.ucsb.cs.cs184.georgelieu.thingamajob;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuewensherryli on 12/6/17.
 */

public class Task {
    public String task_id;
    public String title;
    public String description;
    public double longitude;
    public double latitude;
    public double pay;
    public int year;
    public int month;
    public int day;
    public String original_poster_email;
    public String task_doer_email = null;
    public boolean isFinished = false;    /* indicates if task completed/uncompleted */

    public Task() {

    }

    public Task(String task_id, String title, String description, double longitude, double latitude, double pay, int year, int month, int day, String original_poster_email, String task_doer_email, boolean isFinished) {
        this.task_id = task_id;
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pay = pay;
        this.year = year;
        this.month = month;
        this.day = day;
        this.original_poster_email = original_poster_email;
        this.task_doer_email = task_doer_email;
        this.isFinished = isFinished;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getOriginal_poster_email() {
        return original_poster_email;
    }

    public void setOriginal_poster_email(String original_poster_email) {
        this.original_poster_email = original_poster_email;
    }

    public String getTask_doer_email() {
        return task_doer_email;
    }

    public void setTask_doer_email(String task_doer_email) {
        this.task_doer_email = task_doer_email;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("task_id", getTask_id());
        result.put("title", getTitle());
        result.put("description", getDescription());
        result.put("longitude", getLongitude());
        result.put("latitude", getLatitude());
        result.put("pay", getPay());
        result.put("year", getYear());
        result.put("month", getMonth());
        result.put("day", getDay());
        result.put("original_poster_email", getOriginal_poster_email());
        result.put("task_doer_email", getTask_doer_email());
        result.put("isFinished", getIsFinished());

        return result;
    }
}
