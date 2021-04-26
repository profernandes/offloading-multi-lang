package com.example.anderson.benchimage.dao.model;

import android.graphics.Bitmap;

import java.util.Date;

public final class ResultImage {
    private int id;
    private Date date;

    private long totalTime;
    private long offTime;

    private Bitmap bitmap;
    private AppConfiguration config;

    public ResultImage(AppConfiguration config) {
        this();
        this.config = config;
    }

    public ResultImage() {
        date = new Date();
    }

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final Date getDate() {
        return date;
    }

    public final void setDate(Date date) {
        this.date = date;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public final AppConfiguration getConfig() {
        return config;
    }

    public final void setConfig(AppConfiguration config) {
        this.config = config;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public long getOffTime() { return offTime; }

    public void setOffTime(long offTime) { this.offTime = offTime; }

    @Override
    public String toString() {
        return "ResultImage [id=" + id + ", date=" + date + ", totalTime=" + totalTime + ", config=" + config + "]";
    }
}