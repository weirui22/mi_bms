package org.example.mi_bms.mapper;

import java.util.List;
import org.example.mi_bms.entity.Battery;

public interface BatteryMapper {
    int deleteByPrimaryKey(Integer batteryType);

    int insert(Battery record);

    Battery selectByPrimaryKey(Integer batteryType);

    List<Battery> selectAll();

    int updateByPrimaryKey(Battery record);
}