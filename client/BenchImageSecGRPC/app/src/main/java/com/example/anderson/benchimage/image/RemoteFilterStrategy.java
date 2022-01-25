package com.example.anderson.benchimage.image;

public abstract class RemoteFilterStrategy {
    protected long offloadingTime;

    public RemoteFilterStrategy() { offloadingTime = 0; }

    public abstract byte[] applyFilter(byte source[]);

    public long getOffloadingTime() { return offloadingTime; }

    public void setOffloading_time(long offloading_time) { this.offloadingTime = offloading_time; }
}
