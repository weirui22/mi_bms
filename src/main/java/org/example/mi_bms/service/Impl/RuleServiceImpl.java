package org.example.mi_bms.service.Impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.example.mi_bms.entity.*;
import org.example.mi_bms.mapper.BatteryMapper;
import org.example.mi_bms.mapper.RuleMapper;
import org.example.mi_bms.mapper.VehicleMapper;
import org.example.mi_bms.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 27594
 */
@Component
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
    private RedisTemplate redisTemplate;


    @Override
    public List<WarnRequest> handleRules(String warnMessage) throws Exception {

        //json解析
        List<Message> MessageList = JSONObject.parseArray(warnMessage, Message.class);
        List<WarnRequest> WarnRequestList = new ArrayList<WarnRequest>();
        //随机存放一个车辆信息





        for (Message message : MessageList) {
//            1.读取redis中的汽车信息
            String tempVehicleJson=redisTemplate.opsForValue().get("vehicle").toString();
            List<Vehicle> tempVehicles = JSONObject.parseArray(tempVehicleJson,Vehicle.class);
            boolean flag = false;
            for (Vehicle v : tempVehicles) {
                if (v.getCarid() == message.getCarId()){
                    //在redis找到汽车
                    //找到电池
                    Battery battery = selectBatteryByType(v.getBatteryType());
                    List<Rule> ruleList = selectRules(v.getBatteryType(), message.getWarnId());
                    for (Rule rule : ruleList) {
                        WarnRequest warnRequest = ruleCalculate(rule, message.getSignal());
                        warnRequest.setBatteryType(battery.getName());
                        warnRequest.setCarId(message.getCarId());
                        WarnRequestList.add(warnRequest);
                    }
                    //证明已经找过了
                flag=true;
                }
            }
            //没在redis中找到汽车
            if (!flag){
                //在mysql找到
                Vehicle vehicle = vehicleMapper.selectByCarId(message.getCarId());
                //添加信息到redis中
                addVehicle(tempVehicles,vehicle);
                //找到电池
                Battery battery = selectBatteryByType(vehicle.getBatteryType());
                List<Rule> ruleList = selectRules(vehicle.getBatteryType(), message.getWarnId());
                for (Rule rule : ruleList) {
                    WarnRequest warnRequest = ruleCalculate(rule, message.getSignal());
                    warnRequest.setBatteryType(battery.getName());
                    warnRequest.setCarId(message.getCarId());
                    WarnRequestList.add(warnRequest);
                }
            }


        }
        return WarnRequestList;
    }



    /**
     * redis中找不到信息之后，添加新信息到redis中
     */
    public void addVehicle(List<Vehicle> vehicles, Vehicle vehicle) {
        vehicles.add(vehicle);
        String vehicleJson = JSONObject.toJSONString(vehicles);
        redisTemplate.opsForValue().set("vehicle", vehicleJson);
    }

    /**
     * 根据电池类型找到电池信息
     * @return Battery
     */
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

    /**
     * 根据电池类型和告警类型找到规则信息
     * @return List<Rule>
     */
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


    /**
     *规则计算
     * @return WarnRequest
     */
    public WarnRequest ruleCalculate(Rule rule, String signal) throws Exception {
        String tSignal = signal.substring(1, signal.length() - 1);
        String[] signals = tSignal.split(",");
        List<Detail> details = JSON.parseArray(rule.getDetail(), Detail.class);

        //对每个规则细则进行匹配
        for (Detail detail : details) {
            //设定表达式
            String expression = detail.getExpression();
            String tExpression = expression;
            //将所有值带入表达式中进行计算
            for (String s : signals)
            {
                String left = s.split(":")[0];
                String right = s.split(":")[1];
                left = left.replace("\"", "");
                right = right.replace("\"", "");
                tExpression = tExpression.replace(left, right);
            }//字符串没有变化
            if(tExpression.equals(expression))
            {
                WarnRequest warnRequest = new WarnRequest();
                warnRequest.setWarnName(rule.getName());
                warnRequest.setWarnLevel("匹配失败");
                return warnRequest;
            }else {

                ScriptEngineManager objManager = new ScriptEngineManager();
                ScriptEngine objEngine = objManager.getEngineByName("js");
                boolean bFlag = (boolean) objEngine.eval(tExpression);
                if (bFlag) {

                    WarnRequest warnRequest = new WarnRequest();
                    warnRequest.setWarnName(rule.getName());
                    warnRequest.setWarnLevel(detail.getLevel());
                    return warnRequest;

                }
            }
        }

        WarnRequest warnRequest = new WarnRequest();
        warnRequest.setWarnName(rule.getName());
        warnRequest.setWarnLevel("匹配失败");
        return warnRequest;
    }

}



