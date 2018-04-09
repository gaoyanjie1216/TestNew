package com.gyj.File.Controller;

import com.gyj.Controller.Constant;
import com.gyj.Dao.IUserDao;
import com.gyj.File.Service.IExportService;
import com.gyj.entity.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Gao on 2017/12/14.
 */
@RestController
@RequestMapping(value = "/export", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "导出")
public class ExportController {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IExportService exportService;

    @ApiOperation(value = "导出全部用户信息表")
    @RequestMapping(value = "/allUserList", method = RequestMethod.GET)
    public Map<String, String> exportAllUserList(HttpServletResponse response) throws IOException {

        Map<String, String> result = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
        String fileName = "用户列表" + sdf.format(new Date()) + ".";
        List<UserInfo> allUerList = userDao.findAll();
        if (allUerList.isEmpty()) {
            result.put("message", "用户列表为空!");
            return result;
        }

        String trueName;
        String uuid = UUID.randomUUID() + "";
        trueName = fileName + Constant.EXCEL_TYPE;
        exportService.exportExcel(allUerList, uuid, trueName, response);

        result.put("result", "success");
        result.put("message", "成功");
        result.put("path", uuid);
        result.put("file", trueName);
        return result;
    }


    @ApiOperation(value = "根据用户姓名导出用户列表")
    @RequestMapping(value = "/exportListByUserName", method = RequestMethod.GET)
    public Map<String, String> exportListByUserName(@RequestParam("userName") String userName, HttpServletResponse response) throws IOException {

        Map<String, String> result = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
        String fileName = "用户列表" + sdf.format(new Date()) + ".";
        List<UserInfo> userList = userDao.findByNameContaining(userName);
        if (userList.isEmpty()) {
            result.put("message", "用户列表为空!");
            return result;
        }

        String trueName;
        String uuid = UUID.randomUUID() + "";
        trueName = fileName + Constant.EXCEL_TYPE;
        exportService.exportExcel(userList, uuid, trueName, response);

        result.put("result", "success");
        result.put("message", "成功");
        result.put("path", uuid);
        result.put("file", trueName);
        return result;
    }

}
