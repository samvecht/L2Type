package com.sam.l2type;

import android.util.Log;

/**
 * Created by Sam on 12/4/2015.
 */
public class UserFile {
    private String uname;
    private double cpm;
    private double accuracy;
    private int levels;
    private int autocorrect;
    private int speed;
    private int caps;

    public UserFile() {
        this("", 0, 0, 0, 0, 0, 0);
        Log.e("L2Type", "default constructor");

    }
    public UserFile(String s) {
        if(!s.contains(",")) {
            uname = s;
            cpm = 0;
            accuracy = 0;
            levels = 0;
            autocorrect = 0;
            speed = 0;
            caps = 0;
        } else {
            String[] file = s.split(",");
            uname = file[0];
            cpm = Double.parseDouble(file[1]);
            accuracy = Double.parseDouble(file[2]);
            levels = Integer.parseInt(file[3]);
            autocorrect = Integer.parseInt(file[4]);
            speed = Integer.parseInt(file[5]);
            caps = Integer.parseInt(file[6]);
        }
    }
    public UserFile(String uname, double cpm, double accuracy, int levels, int autocorrect, int speed, int caps) {
        this.uname = uname;
        this.cpm = cpm;
        this.accuracy = accuracy;
        this.levels = levels;
        this.autocorrect = autocorrect;
        this.speed = speed;
        this.caps = caps;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setCpm(double cpm) {
        this.cpm = cpm;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public void setAutocorrect(int autocorrect) {
        this.autocorrect = autocorrect;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setCaps(int caps) {
        this.caps = caps;
    }


    public String getUname() {
        return uname;
    }

    public Double getCpm() {
        return cpm;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public int getLevels() {
        return levels;
    }

    public int getAutocorrect() {
        return autocorrect;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCaps() {
        return caps;
    }

    public String toString() {
        String result = "" + uname +
                "," + cpm +
                "," + accuracy +
                "," + levels +
                "," + autocorrect +
                "," + speed +
                "," + caps;
        Log.e("L2Type", "toString: " + result);
        return result;
    }
}
