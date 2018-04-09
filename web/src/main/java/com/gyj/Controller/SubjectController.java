package com.gyj.Controller;

import com.gyj.Service.ISubjectService;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.xfire.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao on 2017/11/28.
 */
@RestController
@RequestMapping(value = "/subject", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SubjectController {

    @Autowired
    private ISubjectService subjectService;

    @ApiOperation(value = "获取学科")
    @RequestMapping(value = "/getSubjectList", method = RequestMethod.GET)
    public List<Map<String, String>> getSubjectList() throws Exception {

        String wsUrl = "http://172.11.2.16:8080/TQMS/service/TQMSService?wsdl";
        String methodName = "getSubject";
        Client client = null;
        client = new Client(new URL(wsUrl));
        Object[] param = new Object[]{Integer.valueOf(1)};
        Object[] obj = client.invoke(methodName, param);
        String resultXml = obj[0].toString();

        List<Map<String, String>> list = subjectService.xmlToMap(resultXml);

        return list;

    }

    @ApiOperation(value = "根据学科ID获取相应的试卷")
    @RequestMapping(value = "/getPapersListBySubject", method = RequestMethod.GET)
    public List<Map<String, String>> getPapersListBySubject(@RequestParam("subjectID") int subjectID,
                                                            @RequestParam("begin ") int begin,
                                                            @RequestParam("end") int end,
                                                            @RequestParam("paperType") int paperType,
                                                            @RequestParam("orderType") int orderType) throws Exception {

        String wsUrl = "http://172.11.2.16:8080/TQMS/service/TQMSService?wsdl";
        String methodName = "getPapers";
        Client client = null;
        client = new Client(new URL(wsUrl));
        Object[] param = new Object[]{subjectID, begin, end, paperType, orderType};
        Object[] obj = client.invoke(methodName, param);
        String resultXml = obj[0].toString();

        List<Map<String, String>> list = subjectService.xmlToMap(resultXml);
        JSONObject data = new JSONObject();
        data.put("data", JSONArray.fromObject(list));
        Map<String, Object> params = data;
        params.put("isEb", "1");
        System.out.println(params);
        return list;

    }

}
