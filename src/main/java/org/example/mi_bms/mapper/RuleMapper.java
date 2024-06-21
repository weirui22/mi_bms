package org.example.mi_bms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.example.mi_bms.entity.Rule;
import org.springframework.data.domain.Example;

public interface RuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rule record);

    Rule selectByPrimaryKey(Integer id);

    List<Rule> selectAll();

    int updateByPrimaryKey(Rule record);

    List<Rule> selectByBatteryTypeAndWarnId(@Param("batteryType") String batteryType, @Param("warnId") Integer warnId   );
}