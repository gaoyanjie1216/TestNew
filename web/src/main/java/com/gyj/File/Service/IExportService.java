package com.gyj.File.Service;

import com.gyj.entity.UserInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Gao on 2017/12/14.
 */
public interface IExportService {

    /**
     * 导出用户列表
     * @param allUsers
     * @param uuid
     * @param fileName
     * @param response
     */
    void exportExcel(List<UserInfo> allUsers, String uuid, String fileName, HttpServletResponse response);
}
