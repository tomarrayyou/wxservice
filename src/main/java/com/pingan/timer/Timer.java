package com.pingan.timer;

import com.pingan.timer.service.UserTimerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @description: 定时调度任务入口
 * @author: shouwangqingzhong
 * @date: 2019/8/23 9:29
 */
@Component
public class Timer {

    private static Logger logger = LoggerFactory.getLogger(Timer.class);
    @Autowired
    private UserTimerService userTimerService;


    /**
     * @Author: shouwangqingzhong
     * @Description: 到处excel 包含所有用户的信息
     * @Date: 2019/8/23 11:25
     * @Param: []
     * @return: void
     * @version: 3.0.0
     **/
//    @Scheduled(cron = "0 0 16 * * ?")
    @Scheduled(cron = "schedual.batch.leadingOutUsers")
    public void leadingOutUsers(HttpServletResponse response){
        userTimerService.leadigOutUsers(response);
    }
}
