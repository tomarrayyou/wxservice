package com.pingan.common.controller;

import com.alibaba.fastjson.JSON;
import com.pingan.common.entity.User;
import com.pingan.common.model.Page;
import com.pingan.common.model.UserModel;
import com.pingan.common.service.UserService;
import com.pingan.enums.GenderEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("用户相关入口")
@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @ApiOperation(httpMethod = "GET",value = "创建一个用户",notes = "创建一个用户")
    @RequestMapping(value = "/create-one",method = RequestMethod.GET,produces = "application/json")
    public User createOne(){
        logger.info("UserController createOne is begin");
        User user = userService.createUser();
        return user;
    }

    @ApiOperation(httpMethod = "GET",value = "创建用户",notes = "创建用户")
    @RequestMapping(value = "/create-user",method = RequestMethod.GET,produces = "application/json",consumes = "application/json")
    public User createUser(@RequestBody User user){
        logger.info("UserController createUser reqbody is"+ JSON.toJSONString(user));
        return userService.createUser(user);
    }

    @ApiOperation(httpMethod = "GET",value = "根据id查询用户",notes = "根据id查询用户")
    @RequestMapping(value = "/{userId}",method = RequestMethod.GET,produces = "application/json")
    public User getUser(@PathVariable Long userId){
        logger.info("UserController createUser reqbody is"+ userId);
        return userService.getById(userId);
    }


    @ApiOperation(httpMethod = "GET",value = "动态查询用户",notes = "动态查询用户")
    @RequestMapping(value = "/findlist",method = RequestMethod.GET,produces = "application/json")
    public List<User> findList(){
        logger.info("UserController findList  is begin");
        return userService.findByList();
    }

    @ApiOperation(httpMethod = "POST",value = "创建用户并校验",notes = "创建用户并校验")
    @RequestMapping(value = "/valid/test",method = RequestMethod.POST,consumes = "application/json")
    public Boolean createUser(@RequestBody @Valid UserModel userModel, BindingResult result){
        if (result.hasErrors()){
            logger.info("UserController createUser resp"+result.getFieldError().getDefaultMessage());
            return false;
        }
        User user = User.of();
        BeanUtils.copyProperties(userModel,user);
        userService.createUser(user);
        return true;
    }

    @ApiOperation(httpMethod = "GET",value = "原生sql查询用户",notes = "原生sql查询用户")
    @RequestMapping(value = "/get/userlist",method = RequestMethod.GET,produces = "application/json")
    public Page getByEntityManager(){
        logger.info("UserController getByEntityManager begin");
        return userService.getByEntityManager();
    }

    @ApiOperation(httpMethod = "GET",value = "测试更新null值",notes = "测试更新null值")
    @RequestMapping(value = "/get/test/updatenull",method = RequestMethod.GET,produces = "application/json")
    public User testUpdateNull(){
        logger.info("UserController testUpdateNull begin");
        return userService.testUpdateNull();
    }

}
