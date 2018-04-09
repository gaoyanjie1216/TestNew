package com.gyj.Solr.Controller;

import com.gyj.Solr.Entity.TestBO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.gyj.Solr.Utils.SolrServerUtils.getServer;

/**
 * Created by Gao on 2017/12/21.
 */
@RestController
@RequestMapping(value = "/solrBean", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SolrBeanController {

    public static final Log log = LogFactory.getLog(SolrBeanController.class);

    /**
     * 往索引库添加文档
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @ApiOperation(value = "往索引库添加索引", notes = "")
    @RequestMapping(value = "/addIndex", method = RequestMethod.GET)
    public static void addIndex() {
        HttpSolrClient server = getServer();
        List<TestBO> c = new ArrayList<TestBO>();
        try {
            TestBO bo = new TestBO();
            bo.setId("2001");
            bo.setName("mary");
            bo.setScore(89);
            c.add(bo);
            TestBO bo2 = new TestBO();
            bo2.setId("2002");
            bo2.setName("honghong");
            bo2.setScore(100);
            c.add(bo2);
            server.addBeans(c);
            server.commit();
        } catch (SolrServerException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 根据条件删除索引
     *
     * @param condition
     */
    @ApiOperation(value = "根据条件删除索引", notes = "")
    @RequestMapping(value = "/deleteByCondition", method = RequestMethod.GET)
    public static void deleteByCondition(String condition) {
        HttpSolrClient server = getServer();
        try {
            server.deleteByQuery(condition);
            server.commit();
        } catch (SolrServerException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 查询索引
     */
    @ApiOperation(value = "查询索引", notes = "")
    @RequestMapping(value = "/serchSolr", method = RequestMethod.GET)
    public static void serchSolr() {
        HttpSolrClient server = getServer();
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setStart(0);
        query.setRows(10);
        QueryResponse queryResponse;
        try {
            queryResponse = server.query(query);
            //获取list数据有问题
            List<TestBO> list = queryResponse.getBeans(TestBO.class);
            for (TestBO bo : list) {
                System.out.println(bo.getName());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
