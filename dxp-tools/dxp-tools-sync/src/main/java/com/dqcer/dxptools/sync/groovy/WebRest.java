package com.dqcer.dxptools.sync.groovy;

import com.dqcer.dxptools.sync.groovy.dao.RulesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class WebRest {

    @Autowired
    RulesDao rulesDao;


    @RequestMapping(value = "/test")
    public String test() throws Exception {
        List<IRule> list = RuleFactory.getInstance(rulesDao).getRuleList();
        for (IRule iRule : list) {
            iRule.printInfo();
        }
        return "0000";
    }
}
