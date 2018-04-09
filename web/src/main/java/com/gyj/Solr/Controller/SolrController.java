package com.gyj.Solr.Controller;

/**
 * Created by Gao on 2017/11/30.
 */

import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

import static com.gyj.Solr.Utils.SolrServerUtils.getServer;

@RestController
@RequestMapping(value = "/solr", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SolrController {

    //指定solr服务器的地址
    private final static String SOLR_URL = "http://localhost:8983/solr/my_core";

    public static final Log log = LogFactory.getLog(SolrController.class);

     /**
     * 创建SolrServer对象
     * 该对象有两个可以使用，都是线程安全的
     * 1、CommonsHttpSolrServer：启动web服务器使用的，通过http请求的
     * 2、 EmbeddedSolrServer：内嵌式的，导入solr的jar包就可以使用了
     * 3、solr 4.0之后好像添加了不少东西，其中CommonsHttpSolrServer这个类改名为HttpSolrClient
     *
     * @return
     */
    public HttpSolrClient createSolrServer() {

        return new HttpSolrClient(SOLR_URL);
    }

    /**
     * 往索引库添加文档
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @ApiOperation(value = "往索引库添加文档", notes = "")
    @RequestMapping(value = "/addDoc", method = RequestMethod.GET)
    public SolrInputDocument addDoc() throws SolrServerException, IOException {
        //构造一篇文档
        SolrInputDocument document = new SolrInputDocument();
        //往doc中添加字段,在客户端这边添加的字段必须在服务端中有过定义
        document.addField("id", "10");
        document.addField("name", "周星星");
        document.addField("description", "一个小屌丝");
        //获得一个solr服务端的请求，去提交  ,选择具体的某一个solr core
        HttpSolrClient solr = createSolrServer();
        solr.add(document);
        solr.commit();
        solr.close();

        return document;
    }

    @ApiOperation(value = "往索引库添加文档", notes = "")
    @RequestMapping(value = "/addIndex", method = RequestMethod.GET)
    public void addIndex() {
        HttpSolrClient server = getServer();
        Collection<SolrInputDocument> c = new ArrayList<SolrInputDocument>();
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", "1001");
            doc.addField("name", "liming");
            doc.addField("score", "88");
            doc.addField("content", "基于多源信息的");
            c.add(doc);
            SolrInputDocument doc1 = new SolrInputDocument();
            doc1.addField("id", "1002");
            doc1.addField("name", "wangpeng");
            doc1.addField("score", "34");
            doc1.addField("content", "solr总算可以正式工作了");
            c.add(doc1);
            SolrInputDocument doc2 = new SolrInputDocument();
            doc2.addField("id", "1003");
            doc2.addField("name", "基于javabean的添加9");
            doc2.addField("score", "67");
            doc2.addField("content", "solr总算可以正式工作了");
            c.add(doc2);
            server.add(c);
            server.commit();
        } catch (SolrServerException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 根据id从索引库删除文档
     */
    @ApiOperation(value = "根据id从索引库删除文档", notes = "")
    @RequestMapping(value = "/deleteDocById", method = RequestMethod.GET)
    public String deleteDocById(@RequestParam("id") Integer id) throws Exception {

        //选择具体的某一个solr core
        HttpSolrClient solr = createSolrServer();
        //删除文档
        solr.deleteById(String.valueOf(id));
        //删除所有的索引
        //solr.deleteByQuery("*:*");
        //提交修改
        solr.commit();
        solr.close();

        return "OK";
    }

    /**
     * 根据id从索引库删除文档
     */
    @ApiOperation(value = "根据id从索引库删除文档", notes = "")
    @RequestMapping(value = "/deleteDocByIds", method = RequestMethod.GET)
    public String deleteDocByIds(@RequestParam("id") List<String> ids) throws Exception {
        HttpSolrClient server = getServer();
        try {
            ids.add("1001");
            ids.add("1002");
            server.deleteById(ids);
            server.commit();
        } catch (SolrServerException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return "OK";
    }

    /**
     * 查询
     *
     * @throws Exception
     */
    @ApiOperation(value = "查询数据", notes = "")
    @RequestMapping(value = "/querySolr", method = RequestMethod.GET)
    public Map<String, Object> querySolr() throws Exception {

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        HttpSolrClient solrServer = createSolrServer();
        SolrQuery query = new SolrQuery();

        //下面设置solr查询参数
        // query.set("q", "*:*");  // 参数q 查询所有
        //相关查询，比如某条数据某个字段含有周、星、驰三个字  将会查询出来 ，这个作用适用于联想查询
        query.set("q", "周");

        //参数fq, 给query增加过滤查询条件
        //query.addFilterQuery("id:8");

        //给query增加布尔过滤条件,description字段中含有"一个灰常牛逼的军事家"的数据,目前只输入"军事家"不能查出来啊
        //query.addFilterQuery("description:一个灰常牛逼的军事家");

        //参数sort,设置返回结果的排序规则
        query.setSort("id", SolrQuery.ORDER.asc);

        //设置分页参数, 每一页多少值
        query.setStart(0);
        query.setRows(10);

        //参数hl,设置高亮
        query.setHighlight(true);

        //设置高亮的字段
        query.addHighlightField("name");

        //设置高亮的样式
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        //获取查询结果
        QueryResponse response = solrServer.query(query);

        //两种结果获取：得到文档集合或者实体对象
        //查询得到文档的集合
        SolrDocumentList solrDocumentList = response.getResults();

        log.info(solrDocumentList.getNumFound());

        for (SolrDocument doc : solrDocumentList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", doc.get("id"));
            map.put("name", doc.get("name"));
            map.put("description", doc.get("description"));

            list.add(map);
        }

        result.put("list", list);
        result.put("Number", solrDocumentList.getNumFound());

        return result;
    }

    /**
     * 高亮显示，目前调试有问题
     *
     * @throws SolrServerException
     * @throws IOException
     */
    @ApiOperation(value = "高亮显示", notes = "")
    @RequestMapping(value = "/showColor", method = RequestMethod.GET)
    public SolrDocumentList showColor() throws SolrServerException, IOException {
        HttpSolrClient server = getServer();
        SolrQuery query = new SolrQuery(); //高亮字符:基于
        query.setQuery("基于");
        //query.set("q", "基于");
        //参数hl,设置高亮
        query.setHighlight(true);

        //设置高亮的字段
        //query.addHighlightField("name");

        //设置高亮的样式
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");
        //query.setHighlight(true).setHighlightSimplePre("<span class='red'>").setHighlightSimplePost("</span>").setStart(0).setRows(10);
        //hl.fl表示高亮的field，也就是高亮的区域
        query.setParam("hl.fl", "name");  //显示高亮的字段
        QueryResponse res = server.query(query);

        SolrDocumentList solrDocumentList = res.getResults();
        System.out.println("总数：" + solrDocumentList.getNumFound());
      /*  for (SolrDocument sd : solrDocumentList) {
            String id = (String) sd.get("id");
            log.info(id);
            log.info(res.getHighlighting());
            //在solr这里对需要加高亮的字段必须要在索引中的store=true才行
            System.out.println(id + "#" + res.getHighlighting().get(id).get("name"));
        }*/

        Map<String, Map<String, List<String>>> maplist = res.getHighlighting();
        //返回高亮之后的结果..
        for (SolrDocument solrDocument : solrDocumentList) {
            Object id = solrDocument.get("id");
            log.info(maplist);
            Map<String, List<String>> fieldMap = maplist.get(id);
            List<String> stringlist = fieldMap.get("name");
            System.out.println(stringlist);
        }

        return solrDocumentList;
    }

}