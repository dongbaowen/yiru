package com.yiru.dao;

import com.yiru.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Baowen on 2017/4/15.
 */
@Repository
public interface UserDao {

    List<User> getUserList();

}
