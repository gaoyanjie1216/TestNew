package com.gyj.modules.Solr.Entity;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Created by Gao on 2017/12/29.
 */
@SolrDocument(solrCoreName = "my_core")
public class QueryDoc {

    @Id
    private String id;
    @Field("SITE_ID_ls")         private Long[] SITE_ID;

    @Field("SYS_DOCUMENTID_l")  private Long SYS_DOCUMENTID;
    @Field("SYS_DOCLIBID_l")    private Long SYS_DOCLIBID;

}
