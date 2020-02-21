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

    public void setBreaths(int breaths) {
        preferences.edit().putInt("breaths", breaths).apply(); //saving our breaths into a system file
    }

    public int getBreaths() {
        return preferences.getInt("breaths", 0);
    }
}
