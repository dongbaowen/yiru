package com.yiru.web;

import com.yiru.fundamental.json.GsonUtils;
import com.yiru.fundamental.json.Response;
import com.yiru.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Baowen on 2017/4/15.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/list")
    @ResponseBody
    public String getUserList(){
        return new Response<String>().data(GsonUtils.toJson(userService.getUserList())).toString();
    }

}
