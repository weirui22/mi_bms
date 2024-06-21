package org.example.mi_bms.service;

import org.example.mi_bms.entity.Vehicle;

public interface VehicleService {
    // 上传车辆信息
    String updateVehicle(Integer carid,Integer batteryType,Double totalDistance,Integer batteryHealth);
}
