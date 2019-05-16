package com.yiru.service;

import com.yiru.bean.User;
import com.yiru.dao.UserDao;
import org.apache.ibatis.binding.MapperProxy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Baowen on 2017/4/15.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;

    public List<User> getUserList() {
        System.out.println(userDao.getClass().getName());
        System.out.println(userDao instanceof MapperProxy);
        return userDao.getUserList();
    }

}
