package com.funny.combo.tools.controller;

import com.funny.combo.tools.dao.mapper.UnitRuleMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/rule")
public class RuleController {

    @Resource
    private UnitRuleMapper unitRuleMapper;

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("rule_list");
        modelAndView.addObject("ruleList", unitRuleMapper.selectList(null));
        return modelAndView;
    }

}
