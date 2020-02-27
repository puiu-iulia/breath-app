package com.example.breathe.util;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Calendar;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setSessions(int sessions) {
        preferences.edit().putInt("sessions", sessions).apply(); //saving our breaths into a system file
    }

    public int getSessions() {
        return preferences.getInt("sessions", 0);
    }

    public void setDate(long milliseconds) {
        preferences.edit().putLong("seconds", milliseconds).apply(); //saving our breaths into a system file
    }

    public String getDate() {
        long milliDate = preferences.getLong("seconds", 0);
        String amOrPm;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliDate);
        int a = calendar.get(Calendar.AM_PM);
        if (a == Calendar.AM) {
            amOrPm = "AM";
        } else {
            amOrPm = "PM";
        }

        String time = "Last at " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " " + amOrPm;
        return time;
    }

    public void setInhale(int inhale) {
        preferences.edit().putInt("inhale", inhale).apply();
    }

    public int getInhale() {
        return preferences.getInt("inhale", 0);
    }

    public void setExhale(int exhale) {
        preferences.edit().putInt("exhale", exhale).apply();
    }

    public int getExhale() {
        return preferences.getInt("exhale", 0);
    }

    public void setInhalehold(int inhaleHold) {
        preferences.edit().putInt("inhaleHold", inhaleHold).apply();
    }

    public int getInhaleHold() {
        return preferences.getInt("inhaleHold", 0);
    }

    public void setExhaleHold(int exhaleHold) {
        preferences.edit().putInt("exhaleHold", exhaleHold).apply();
    }

    public int getExhaleHold() {
        return preferences.getInt("exhaleHold", 0);
    }

    public void setBreaths(int breaths) {
        preferences.edit().putInt("breaths", breaths).apply(); //saving our breaths into a system file
    }

    public int getBreaths() {
        return preferences.getInt("breaths", 0);
    }

    public void setTime(int time) {
        preferences.edit().putInt("time", time).apply();
    }

    public int getTime() {
        return preferences.getInt("time", 1);
    }
}
