package org.example.mi_bms.entity;

import java.util.Date;

public class Rule {
    private Integer id;

    private Integer warnId;

    private String name;

    private Integer batteryType;

    private String detail;

    private Date createTime;

    private Date updateTime;

    private Integer delete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarnId() {
        return warnId;
    }

    public void setWarnId(Integer warnId) {
        this.warnId = warnId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(Integer batteryType) {
        this.batteryType = batteryType;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }
}