package com.gyj.modules.File.Service.Impl;

import com.gyj.modules.File.Service.IExportService;
import com.gyj.modules.user.model.UserInfo;
import com.gyj.modules.user.utils.Constant;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao on 2017/12/14.
 */
@Service
public class ExportServiceImpl implements IExportService {

    private static Logger logger = LoggerFactory.getLogger(ExportServiceImpl.class);

    @Override
    public void exportExcel(List<UserInfo> allUsers, String uuid, String fileName, HttpServletResponse response) {

        Workbook wb = new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet("sheet1");

        List<String> titles = new ArrayList<>();
        titles.add("姓名");
        titles.add("地址");
        titles.add("年龄");
        titles.add("密码");
        titles.add("创建时间");
        titles.add("最后修改时间");

        Row row = sheet1.createRow(0);
        for (int i = 0; i < titles.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
        }

        for (int i = 1; i < allUsers.size() + 1; i++) {
            Row rowContent = sheet1.createRow(i);
            // 构造一行数据
            List<String> contents = new ArrayList<>();
            UserInfo user = allUsers.get(i - 1);
            contents.add(user.getName());
            contents.add(user.getAddress());
            contents.add(user.getAge() == null ? "" : String.valueOf(user.getAge()));
            contents.add(user.getPassword());
            contents.add(String.valueOf(user.getCreateTime()));
            contents.add(String.valueOf(user.getLastmodifiedTime()));

            // contents长度应该和titles一样
            for (int j = 0; j < titles.size(); j++) {
                Cell cellContent = rowContent.createCell(j);
                cellContent.setCellValue(contents.get(j));
            }
        }

        FileInputStream fis = null;
        OutputStream out = null;
        try {
            String path = System.getProperty("java.io.tmpdir");
            File temp = new File(path, fileName);
            FileOutputStream fos = new FileOutputStream(temp);
            wb.write(fos);
            fos.flush();
            fos.close();
            fis = new FileInputStream(temp);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            out = response.getOutputStream();
            IOUtils.copy(fis, out);
        } catch (IOException e) {
            logger.error(Constant.ERROR_MESSAGE, e);
        } finally {
            if (null != fis) {
                IOUtils.closeQuietly(fis);
            }
            if (null != out) {
                IOUtils.closeQuietly(out);
            }
        }
    }

}
