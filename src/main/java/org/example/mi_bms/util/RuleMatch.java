package org.example.mi_bms.util;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.util.StringUtils;
import org.example.mi_bms.entity.Detail;
import org.example.mi_bms.entity.Rule;
import org.example.mi_bms.entity.WarnRequest;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;


@Component
public class RuleMatch {

    //匹配算式，每次传过来一个大类规则，然后进行自定义匹配
    public static WarnRequest matchType(Rule rule, String signal) throws Exception {
        String tSignal = signal.substring(1, signal.length() - 1);
        String[] signals = tSignal.split(",");
        List<Detail> details = JSON.parseArray(rule.getDetail(), Detail.class);

    //对每个规则细则进行匹配
        for (Detail detail : details) {
            String expression = detail.getExpression();//设定表达式
            String tExpression = expression;
            for (String s : signals)//将所有值带入表达式中进行计算
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


