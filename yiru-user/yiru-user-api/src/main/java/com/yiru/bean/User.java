package com.yiru.bean;

import java.io.Serializable;

/**
 * Created by Baowen on 2017/4/15.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 6684149117871726748L;

    private Long id;
    private String username;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
