package org.example.mi_bms.service.Impl;

import org.example.mi_bms.entity.Vehicle;
import org.example.mi_bms.mapper.VehicleMapper;
import org.example.mi_bms.service.VehicleService;
import org.example.mi_bms.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("vehicleService")
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private UUID uuid;


    @Override
    public String updateVehicle(Integer carid, Integer batteryType, Double totalDistance, Integer batteryHealth) {

        Vehicle vehicle = new Vehicle();
        vehicle.setVid(uuid.generateUUID());
        // carId 设置自增，利于之后的查询
//        vehicle.setCarid(carid);
        vehicle.setBatteryType(batteryType);
        vehicle.setTotalDistance(totalDistance);
        vehicle.setBatteryHealth(batteryHealth);
        vehicle.setDelete(0);
        vehicle.setCreateTime(new Date());
        vehicle.setUpdateTime(new Date());
        vehicleMapper.insert(vehicle);
        return "上传成功";
    }
}
