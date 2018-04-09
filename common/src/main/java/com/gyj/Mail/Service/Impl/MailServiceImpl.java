package com.gyj.Mail.Service.Impl;

import com.gyj.Mail.Service.IMailService;
import com.gyj.Mail.enums.MailContentTypeEnum;
import com.gyj.Mail.utils.PropertiesUtil;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.List;
import java.util.Properties;

/**
 * Created by Gao on 2017/12/13.
 */
@Service
public class MailServiceImpl implements IMailService {

    @Override
    public void mailSendMessage(String title, String content, String contentType, List<String> targets) throws Exception {

        //读取/resource/mail_zh_CN.properties文件内容
        final PropertiesUtil properties = new PropertiesUtil("mail");

        // 创建Properties 类用于记录邮箱的一些属性
        final Properties props = new Properties();

        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", properties.getValue("mail.smtp.service"));
        //设置端口号，QQ邮箱给出了两个端口465/587
        props.put("mail.smtp.port", properties.getValue("mail.smtp.prot"));
        // 设置发送邮箱
        props.put("mail.user", properties.getValue("mail.from.address"));
        // 设置发送邮箱的16位STMP口令
        props.put("mail.password", properties.getValue("mail.from.smtp.pwd"));

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };

        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        String nickName = MimeUtility.encodeText(properties.getValue("mail.from.nickname"));
        InternetAddress form = new InternetAddress(nickName + " <" + props.getProperty("mail.user") + ">");
        message.setFrom(form);

        // 设置邮件标题
        message.setSubject(title);

        //html发送邮件
        if (contentType != null) {
            if (contentType.equals(MailContentTypeEnum.HTML.getValue())) {
                // 设置邮件的内容体
                message.setContent(content, contentType);
            }
            //文本发送邮件
            if (contentType.equals(MailContentTypeEnum.TEXT.getValue())) {
                message.setText(content);
            }
        }

        //发送邮箱地址
        for (int i = 0; i < targets.size(); i++) {
            try {
                // 设置收件人的邮箱
                InternetAddress to = new InternetAddress(targets.get(i));
                message.setRecipient(Message.RecipientType.TO, to);

                //发送邮件
                Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
