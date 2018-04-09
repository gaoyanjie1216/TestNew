package com.gyj.File.Controller;

/**
 * Created by Gao on 2017/11/27.
 */

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * 码云：http://git.oschina.net/jnyqy
 */
//@RestController注解集成了@ResponseBody注解和@Controller注解,加@ResponseBody就不能返回页面了
@Controller
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FileController {

    //通过Spring的autowired注解获取spring默认配置的request
    @Autowired
    private HttpServletRequest request;

    /**
     * 初始化上传文件界面，跳转到index.jsp
     *
     * @return
     */
    @ApiOperation(value = "初始化上传文件界面，跳转到index.jsp", notes = "")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * 提取上传方法为公共方法
     *
     * @param uploadDir 上传文件目录
     * @param file      上传对象
     * @throws Exception
     */
    private void executeUpload(String uploadDir, MultipartFile file) throws Exception {

        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        //suffix1 只是输出后缀名，不包括.
        //String suffix1 = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1, file.getOriginalFilename().length());
        //上传文件名
        String filename = UUID.randomUUID() + suffix;
        //服务器端保存的文件对象
        File serverFile = new File(uploadDir + filename);
        //将上传的文件写入到服务器端文件内, transferTo(File dest)这个方法来转存文件到指定的路径
        file.transferTo(serverFile);
    }

    /**
     * 上传文件方法
     *
     * @param file 前台上传的文件对象
     * @return
     */
    @ApiOperation(value = "上传单个文件", notes = "")
    @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadOneFile(HttpServletRequest request, MultipartFile file) {

        try {
            //request.getSession().getServletContext().getRealPath("/"), 获取Web项目的全路径，tomcat下webapps下的项目根路径
            //equest.getSession().getServletContext() 获取的是Servlet容器对象，相当于tomcat容器了
            // getRealPath("/") 获取实际路径，“/”指代项目根目录，所以代码返回的是项目在容器中的实际发布运行的根路径
//            String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
            String uploadDir = "D:\\projects\\Test\\web\\src\\main\\webapp\\upload\\";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //调用上传方法
            executeUpload(uploadDir, file);
        } catch (Exception e) {
            //打印错误堆栈信息
            e.printStackTrace();
            return "上传失败";
        }

        return "上传成功";
    }

    /**
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "上传单个文件", notes = "")
    @RequestMapping(value = "/uploadOneFile1", method = RequestMethod.POST)
    @ResponseBody
    public String uploadOneFile1(@RequestParam("file") MultipartFile file) {

        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
                //如果目录不存在，自动创建文件夹
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //调用上传方法
                executeUpload(uploadDir, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "上传成功";
    }

    /**
     * 上传多个文件
     *
     * @param request 请求对象
     * @param file    上传文件集合
     * @return
     */
    @ApiOperation(value = "上传多个文件", notes = "")
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFiles(HttpServletRequest request, MultipartFile[] file) {

        try {
            //上传目录地址
            String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //遍历文件数组执行上传
            for (int i = 0; i < file.length; i++) {
                if (file[i] != null) {
                    //调用上传方法
                    executeUpload(uploadDir, file[i]);
                }
            }
        } catch (Exception e) {
            //打印错误堆栈信息
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }
}