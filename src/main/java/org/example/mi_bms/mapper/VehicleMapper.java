package org.example.mi_bms.mapper;

import java.util.List;
import org.example.mi_bms.entity.Vehicle;

public interface VehicleMapper {
    int deleteByPrimaryKey(String vid);

    int insert(Vehicle record);

    Vehicle selectByPrimaryKey(String vid);

    List<Vehicle> selectAll();

    int updateByPrimaryKey(Vehicle record);

    Vehicle selectByCarId(Integer carid);
}