package org.example.mi_bms.entity;

public class WarnRequest {
    private Integer carId;
    private String batteryType;
    private String warnName;
    private String warnLevel;


//    WarnRequest(Integer carId, String batteryType, String warnName, Integer warnLevel) {
//        this.carId = carId;
//        this.batteryType = batteryType;
//        this.warnName = warnName;
//        this.warnLevel = warnLevel;
//    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }

    public String getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(String warnLevel) {
        this.warnLevel = warnLevel;
    }
}
