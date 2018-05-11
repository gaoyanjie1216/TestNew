package com.gyj.modules.Solr.Entity;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 * Created by Gao on 2017/12/21.
 */
public class TestBO implements Serializable {

    public TestBO() {

    }

    public TestBO(String id, String name, int score, String[] content) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.content = content;
    }

    @Field
    private String id;

    @Field
    private String name;

    @Field
    private int score;

    //多值，对应 multiValued="true"
    private String[] content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String[] getContent() {
        return content;
    }

    @Field
    public void setContent(String[] content) {
        this.content = content;
    }

}
