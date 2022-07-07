package com.jb.mareu.model;

public class Heure {
    private int mHeure;
    private int mMinute;

    public Heure(){
        mHeure = 12;
        mMinute = 0;
    }

    public Heure(int heure, int minute)
    {
        if (checkHeure(heure)) {
            mHeure = heure;
        }
        else {
            mHeure = 0;
        }

        if(checkMinute(minute)) {
            mMinute = minute;
        }
        else {
            mMinute = 0;
        }
    }

    public int getHeure() {
        return mHeure;
    }

    public void setHeure(int heure) {
        if (checkHeure(heure)) {
            mHeure = heure;
        }
        else {
            mHeure = 0;
        }
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        if(checkMinute(minute)) {
            mMinute = minute;
        }
        else {
            mMinute = 0;
        }
    }

    private boolean checkHeure(int heure){
        if (heure >= 1 && heure <= 23) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkMinute(int minute){
        if(minute >= 0 && minute <= 59) {
            return true;
        }
        else {
            return false;
        }
    }
}
