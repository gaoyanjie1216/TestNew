package com.gyj.Solr.Dao;

import com.gyj.Solr.Entity.QueryDoc;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * Created by Gao on 2017/12/29.
 */
public interface ISolrQueryDocDao extends SolrCrudRepository<QueryDoc, String> {

}
