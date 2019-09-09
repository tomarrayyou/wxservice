package com.pingan.common.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("通用入口类")
@RestController
@RequestMapping("/common")
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping(value = "startup",method = RequestMethod.GET)
    public String startUp(){
        logger.info("CommonController startUp is start");
        return "Hello Spring-Boot";
    }
}
