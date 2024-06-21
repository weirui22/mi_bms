package org.example.mi_bms.entity;

import lombok.Getter;

public class Message {
    private int carId;
    private int warnId;
    private String signal;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getWarnId() {
        return warnId;
    }

    public void setWarnId(int warnId) {
        this.warnId = warnId;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }
}
