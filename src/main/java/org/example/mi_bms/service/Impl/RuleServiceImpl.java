package org.example.mi_bms.service.Impl;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.example.mi_bms.entity.*;
import org.example.mi_bms.mapper.BatteryMapper;
import org.example.mi_bms.mapper.RuleMapper;
import org.example.mi_bms.mapper.VehicleMapper;
import org.example.mi_bms.service.RuleService;
import org.example.mi_bms.util.RuleMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Slf4j
@Service("RuleService")
public class RuleServiceImpl implements RuleService {
    @Autowired
    private RuleMapper ruleMapper;
    @Autowired
    private VehicleMapper vehicleMapper;
    @Autowired
    private BatteryMapper batteryMapper;
    @Autowired
    private RuleMatch ruleMatch;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<WarnRequest> handleRules(String warnMessage) throws Exception {

        //json解析
        List<Message> MessageList = JSONObject.parseArray(warnMessage, Message.class);
        List<WarnRequest> WarnRequestList = new ArrayList<WarnRequest>();
        //随机存放一个车辆信息

        //电池信息全部存储
        List<Battery> batteries = batteryMapper.selectAll();
        String battaryJson = JSONObject.toJSONString(batteries);
        redisTemplate.opsForValue().set("batteries", battaryJson);
        //汽车信息部分存储，后选择存储
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(vehicleMapper.selectByCarId(MessageList.get(0).getCarId()));
        String vehicleJson = JSONObject.toJSONString(vehicles);
        redisTemplate.opsForValue().set("vehile", vehicleJson);
        //规则信息全部存储
        List<Rule> rules = ruleMapper.selectAll();
        String ruleJson = JSONObject.toJSONString(rules);
        redisTemplate.opsForValue().set("rules", ruleJson);



        for (Message message : MessageList) {
//            1.读取redis中的汽车信息
            String tempVehicleJson=redisTemplate.opsForValue().get("vehile").toString();
            List<Vehicle> tempVehicles = JSONObject.parseArray(tempVehicleJson,Vehicle.class);
            boolean flag = false;
            for (Vehicle v : tempVehicles) {
                if (v.getCarid() == message.getCarId()){//在redis找到汽车
                    Battery battery = selectBatteryByType(v.getBatteryType());//找到电池
                    List<Rule> ruleList = selectRules(v.getBatteryType(), message.getWarnId());
                    for (Rule rule : ruleList) {
                        WarnRequest warnRequest = ruleMatch.matchType(rule, message.getSignal());
                        warnRequest.setBatteryType(battery.getName());
                        warnRequest.setCarId(message.getCarId());
                        WarnRequestList.add(warnRequest);
                    }
                flag=true;//证明已经找过了
                }
            }
            //没在redis中找到汽车
            if (!flag){
                Vehicle vehicle = vehicleMapper.selectByCarId(message.getCarId());//在mysql找到
                addVehicle(tempVehicles,vehicle);//添加信息到redis中
                Battery battery = selectBatteryByType(vehicle.getBatteryType());//找到电池
                List<Rule> ruleList = selectRules(vehicle.getBatteryType(), message.getWarnId());
                for (Rule rule : ruleList) {
                    WarnRequest warnRequest = ruleMatch.matchType(rule, message.getSignal());
                    warnRequest.setBatteryType(battery.getName());
                    warnRequest.setCarId(message.getCarId());
                    WarnRequestList.add(warnRequest);
                }
            }


        }
        return WarnRequestList;
    }
    public void addVehicle(List<Vehicle> vehicles, Vehicle vehicle) {
        vehicles.add(vehicle);
        String vehicleJson = JSONObject.toJSONString(vehicles);
        redisTemplate.opsForValue().set("vehile", vehicleJson);
    }

    public Battery selectBatteryByType(int batteryType ) {
        String tempBatteryJson=redisTemplate.opsForValue().get("batteries").toString();
        List<Battery> tempBatteries = JSONObject.parseArray(tempBatteryJson,Battery.class);
        for (Battery battery : tempBatteries) {
            if(battery.getBatteryType()==batteryType){
                return battery;
            }
        }
        return null;
    }

    public List<Rule> selectRules(Integer batteryType,Integer warnId) {
        String tempRuleJson = redisTemplate.opsForValue().get("rules").toString();
        List<Rule> tempRules = JSONObject.parseArray(tempRuleJson, Rule.class);
        List<Rule> rules = new ArrayList<Rule>();
        for (Rule rule : tempRules) {
            if(rule.getBatteryType().equals(batteryType)){
                if(rule.getWarnId().equals(warnId)){
                    rules.add(rule);
                    return rules;
                } else if (warnId.equals(0)) {
                    rules.add(rule);
                }
            }
        }
        return rules;
    }
}



