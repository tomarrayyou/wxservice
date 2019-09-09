package com.pingan.timer.service;

import com.pingan.common.entity.User;
import com.pingan.common.service.UserService;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 用户定时调度任务服务实现类
 * @author: shouwangqingzhong
 * @date: 2019/8/23 11:30
 */
@Service
public class UserTimerService {

    private static Logger logger = LoggerFactory.getLogger(UserTimerService.class);
    @Autowired
    private UserService userService;

    /*
     * @Author: shouwangqingzhong
     * @Description: excel到处所有的用户
     * @Date: 2019/8/23 11:33
     * @Param: []
     * @return: void
     * @version: 3.0.0
     **/
    public void leadigOutUsers(HttpServletResponse response){
        //获取所有数据
        List<User> all = userService.findAll();
        //开始导出
        exportExcel(all,response);
    }

    /*
     * @Author: shouwangqingzhong
     * @Description: 使用excel导出所有的用户
     * @Date: 2019/8/23 17:52
     * @Param: [all, response]
     * @return: void
     * @version: 3.0.0
     **/
    public void exportExcel(List<User> all, HttpServletResponse response){
        //设置默认文件名
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();

        //设置头信息
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition","attachment: filename="+fileName+"用户追踪.xls");
        //改成输出Excel文件
        response.setContentType("application/vnd.ms-excel");

        try {
            WritableWorkbook wwb = Workbook.createWorkbook(response.getOutputStream());
            WritableCellFormat titleFormat = new WritableCellFormat();
            titleFormat.setAlignment(Alignment.CENTRE);
            titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            WritableSheet sheet = wwb.createSheet("用户列表", 0);
            Label userTitleLable = new Label(0, 0, "用户列表", titleFormat);

            //初始化表头信息
            List<Label> userHeadList = new ArrayList<>();
            userHeadList.add(new Label(0,1,"用户Id",cellFormat));
            userHeadList.add(new Label(1,1,"用户姓名",cellFormat));
            userHeadList.add(new Label(2,1,"用户手机号",cellFormat));
            userHeadList.add(new Label(3,1,"用户性别",cellFormat));
            userHeadList.add(new Label(4,1,"表记录创建日期",cellFormat));

            try {
                sheet.addCell(userTitleLable);
                sheet.mergeCells(0,0,4,0);
                for (Label label : userHeadList) {
                    sheet.addCell(label);
                }
            } catch (WriteException e) {
                e.printStackTrace();
            }

            //填充数据
            if (null != all && all.size() > 0){
                for (int i = 0; i < all.size(); i++) {
                    User user = all.get(i);
                    int rowCount = i + 2;
                    int colCount = 0;
                    sheet.addCell(new Label(colCount++,rowCount,null == user.getId()?"":user.getId()+"",cellFormat));
                    sheet.addCell(new Label(colCount++,rowCount,null == user.getUserName()?"":user.getUserName(),cellFormat));
                    sheet.addCell(new Label(colCount++,rowCount,null == user.getMobile()?"":user.getMobile(),cellFormat));
                    sheet.addCell(new Label(colCount++,rowCount,null == user.getGender()?"":user.getGender().getDescription(),cellFormat));
                    sheet.addCell(new Label(colCount++,rowCount,null == user.getCreateDate()?"":user.getCreateDate()+"",cellFormat));
                }
            }

            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
