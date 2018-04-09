package com.gyj.Service.Impl;

import com.gyj.Service.ISubjectService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao on 2017/11/28.
 */
//@Service注解，将自动注册到Spring容器，不需要再在定义bean了
@Service
@Transactional(readOnly = true)
public class SubjectServiceImpl implements ISubjectService {

    @Override
    public List<Map<String, String>> xmlToMap(String resultXml) {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            //将xml转为dom对象
            Document doc = DocumentHelper.parseText(resultXml);
            //获取根节点
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();

            for (Element obj : elements) {
                Map<String, String> map = new HashMap<String, String>();
                //遍历子元素
                List<Element> eles = obj.elements();
               /* for (Element var : eles) {
                    map.put(var.getName(), var.getText());
                }*/
                eles.forEach(var -> {
                    map.put(var.getName(), var.getText());
                });
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
