package com.gyj.Mail.Service;

import java.util.List;

/**
 * Created by Gao on 2017/12/13.
 */
public interface IMailService {

    public void mailSendMessage(String title, String content, String contentType, List<String> targets) throws Exception;
}
