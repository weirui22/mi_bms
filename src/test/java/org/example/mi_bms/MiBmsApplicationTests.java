package org.example.mi_bms;

import org.example.mi_bms.entity.Rule;
import org.example.mi_bms.entity.WarnRequest;
import org.example.mi_bms.mapper.RuleMapper;
import org.example.mi_bms.service.Impl.RuleServiceImpl;
import org.example.mi_bms.service.RuleService;
import org.example.mi_bms.util.RuleMatch;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class MiBmsApplicationTests {

    @Autowired
    private RuleMatch ruleMatch;
    @Autowired
    private RuleMapper ruleMapper;
    @Test
    void testCalculate() throws Exception{
        Rule rule = ruleMapper.selectByPrimaryKey(1);
        String signal = "{\\\"Mx\\\":51.0,\\\"Mi\\\":8.6}";
        WarnRequest warnRequest=ruleMatch.matchType(rule,signal);
        System.out.println(warnRequest.getWarnName());
    }

}
