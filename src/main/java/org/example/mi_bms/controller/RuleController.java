package org.example.mi_bms.controller;


import org.example.mi_bms.entity.WarnRequest;
import org.example.mi_bms.response.R;
import org.example.mi_bms.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/warn")
public class RuleController {

    @Qualifier("RuleService")
    @Autowired
    private RuleService ruleService;

    @PostMapping
    public R updateWarn(@RequestBody String wanrnMessage)throws Exception {
        List<WarnRequest> result;
        try {
            result = ruleService.handleRules(wanrnMessage);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        return R.success(result);

    }

}
