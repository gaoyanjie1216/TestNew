package com.gyj.Solr.Utils;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * Created by Gao on 2017/12/21.
 */
public class SolrServerUtils {

    private static HttpSolrClient server = null;
    private static String SOLR_URL = "http://localhost:8983/solr/solr_sample";

    public static HttpSolrClient getServer() {
        if (server == null) {
            server = new HttpSolrClient(SOLR_URL);
            server.setDefaultMaxConnectionsPerHost(1000);
            server.setMaxTotalConnections(10000);
            server.setConnectionTimeout(60000);    //设置连接超时时间（单位毫秒） 1000
            server.setSoTimeout(60000);            //设置读数据超时时间(单位毫秒) 1000
            server.setFollowRedirects(false);      //遵循从定向
            server.setAllowCompression(true);      //允许压缩
        }

        return server;
    }

}
