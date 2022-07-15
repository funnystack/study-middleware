package com.funny.combo.tools.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author fangli 2022-03-18 09:36
 **/
@Controller
public class IndexController {

    @Value("${server.port:8080}")
    private String serverPort;


    @RequestMapping("/")
    public ModelAndView call() {
        ModelAndView modelAndView = new ModelAndView("call");
        modelAndView.addObject("serverPort", this.serverPort);
        return modelAndView;
    }
}
