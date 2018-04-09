package com.gyj.Mail.Controller;

/**
 * Created by Gao on 2017/12/13.
 */

import com.gyj.Mail.Entity.MailEntity;
import com.gyj.Mail.Service.IMailService;
import com.gyj.Mail.enums.MailContentTypeEnum;
import com.gyj.Mail.utils.PropertiesUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.List;
import java.util.Properties;

/**
 * 码云：http://git.oschina.net/jnyqy
 */
@RestController
@RequestMapping(value = "/mail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MailController {

    @Autowired
    private IMailService mailService;

    //邮件实体
    private static MailEntity mail = new MailEntity();

    /**
     * 执行发送邮件
     *
     * @throws Exception 如果发送失败会抛出异常信息
     */
    @ApiOperation(value = "发送邮件信息", notes = "")
    @RequestMapping(value = "/sendMail", method = RequestMethod.GET)
    public void sendMail(@ApiParam(value = "邮件标题") @RequestParam("title") String title,
                         @ApiParam(value = "邮件内容") @RequestParam("content") String content,
                         @ApiParam(value = "邮件格式html和text,默认使用text内容发送") @RequestParam(value = "contentType", required = false) String contentType,
                         @ApiParam(value = "目标邮件地址") @RequestParam("targets") List<String> targets) throws Exception {

        //默认使用text内容发送
        if (contentType == null) {
            contentType = MailContentTypeEnum.TEXT.getValue();
        }
        if (title == null || title.trim().length() == 0) {
            throw new Exception("邮件标题没有设置.调用title方法设置");
        }
        if (content == null || content.trim().length() == 0) {
            throw new Exception("邮件内容没有设置.调用content方法设置");
        }
        if (targets.isEmpty()) {
            throw new Exception("没有接受者邮箱地址.调用targets方法设置");
        }

        mailService.mailSendMessage(title, content, contentType, targets);
        
    }
}