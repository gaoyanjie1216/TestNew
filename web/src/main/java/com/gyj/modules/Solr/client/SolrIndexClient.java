package com.gyj.modules.Solr.client;

import com.gyj.modules.Solr.Entity.QueryDoc;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.server.support.SolrClientUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao on 2017/12/29.
 */
@Component
public class SolrIndexClient {

    private Logger logger = LoggerFactory.getLogger(SolrIndexClient.class);

    private final String coreName = QueryDoc.class.getAnnotation(SolrDocument.class).solrCoreName();
    private HttpSolrClient solrClient;

    @Autowired
    private void setSolrTemplate(SolrClient solrClient) {
        SolrTemplate solrTemplate = new SolrTemplate(SolrClientUtils.clone(solrClient), coreName);
        solrTemplate.afterPropertiesSet();
        this.solrClient = (HttpSolrClient) solrTemplate.getSolrClient();
    }

    /**
     * 插入索引，如果存在则更新
     *
     * @param document
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Transactional
    public String insertIndex(Map<String, Object> document) throws IOException, SolrServerException {
        if (!document.keySet().contains("id") || document.get("id") == null) {
            return null;
        }
        String id = document.get("id").toString();
        if (StringUtils.isNotBlank(id)) {
            solrClient.deleteById(id);
            solrClient.commit();
            SolrInputDocument indexDocument = convertJsonToDocument(document);
            SolrResponse response = solrClient.add(indexDocument);
            solrClient.commit();
            return response.toString();
        }
        return null;
    }

    /**
     * 批量插入索引，如果存在则更新
     *
     * @param documents
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Transactional
    public String insertIndex(List<Map<String, Object>> documents) throws IOException, SolrServerException {
        List<SolrInputDocument> inputDocuments = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (Map<String, Object> document : documents) {
            ids.add(MapUtils.getString(document, "id"));
            inputDocuments.add(convertJsonToDocument(document));
        }
        logger.debug("更新索引:" + ids.toString());

        SolrResponse deleteResponse = solrClient.deleteById(ids);
        solrClient.commit();
        logger.debug("删除反馈：" + deleteResponse.toString());

        SolrResponse response = solrClient.add(inputDocuments);
        solrClient.commit();
        logger.debug("插入反馈：" + response.toString());

        return response.toString();
    }

    /**
     * 通过ID删除索引
     *
     * @param id
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Transactional
    public String deleteIndex(String id) throws IOException, SolrServerException {
        SolrResponse response = solrClient.deleteById(id);
        solrClient.commit();
        return response.toString();
    }

    /**
     * 通过ID列表删除索引
     *
     * @param ids
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Transactional
    public String deleteIndex(List<String> ids) throws IOException, SolrServerException {
        SolrResponse response = solrClient.deleteById(ids);
        solrClient.commit();
        return response.toString();
    }

    @Transactional
    public String deleteBySiteId(Long siteId) throws IOException, SolrServerException {
        SolrResponse response = solrClient.deleteByQuery("SITE_ID_s:_" + siteId);
        solrClient.commit();
        return response.toString();
    }

    /**
     * 删除全部索引
     *
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Transactional
    public String deleteAllIndex() throws IOException, SolrServerException {
        SolrResponse response = solrClient.deleteByQuery("*:*");
        solrClient.commit();
        return response.toString();
    }

    /**
     * JSONObject转化为solr文档
     *
     * @param document 待转换的doc对象
     * @return
     */
    private SolrInputDocument convertJsonToDocument(Map<String, Object> document) {
        SolrInputDocument solrInputDocument = new SolrInputDocument();

        for (Map.Entry<String, Object> entry : document.entrySet()) {
            solrInputDocument.addField(entry.getKey(), entry.getValue());
        }

        return solrInputDocument;
    }


}
