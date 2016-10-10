package com.tiaoling.cloud.conf.controller;

import com.tiaoling.cloud.conf.annotation.PermessionLimit;
import com.tiaoling.cloud.conf.interceptor.PermissionInterceptor;
import com.tiaoling.cloud.conf.utils.CommonPropertiesUtils;
import com.tiaoling.cloud.conf.utils.HttpUtils;
import com.tiaoling.cloud.conf.utils.JsonUtils;
import com.tiaoling.cloud.conf.utils.ReturnT;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by yhl on 2016/10/9.
 */
@Controller
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class.getName());

    @RequestMapping("/")
    @PermessionLimit(limit=false)
    public String index(Model model, HttpServletRequest request) {
        if (!PermissionInterceptor.ifLogin(request)) {
            return "redirect:/toLogin";
        }
        return "redirect:/conf";
    }
    @RequestMapping("/toLogin")
    @PermessionLimit(limit=false)
    public String toLogin(Model model, HttpServletRequest request) {
        if (PermissionInterceptor.ifLogin(request)) {
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(value="login", method= RequestMethod.POST)
    @ResponseBody
    @PermessionLimit(limit=false)
    public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String userName, String password, String ifRemember){
        String json="账号或密码错误";
        String doMethod = CommonPropertiesUtils.get("login");
        Map<String,Object> params = new HashMap<String,Object>();
        if (!PermissionInterceptor.ifLogin(request)) {
            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password))
            {
                boolean ifRem = false;
                if (StringUtils.isNotBlank(ifRemember) && "on".equals(ifRemember)) {
                    ifRem = true;
                }
                json = HttpUtils.doPost(CommonPropertiesUtils.get("cloud_conf_api_url") + "/"
                        + doMethod, JsonUtils.getJSONString(params));
                if(json.equals("SUCCESS"))
                PermissionInterceptor.login(response, ifRem);
                return ReturnT.SUCCESS;
            }
            else
            {
                return new ReturnT<String>(500, "账号或密码错误");
            }
//            Properties prop = PropertiesUtil.loadProperties("config.properties");
//            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)
//                    && PropertiesUtil.getString(prop, "login.username").equals(userName)
//                    && PropertiesUtil.getString(prop, "login.password").equals(password)) {
//                boolean ifRem = false;
//                if (StringUtils.isNotBlank(ifRemember) && "on".equals(ifRemember)) {
//                    ifRem = true;
//                }
//                PermissionInterceptor.login(response, ifRem);
//            } else {
//                return new ReturnT<String>(500, "账号或密码错误");
//            }
        }
        return new ReturnT<String>(500, "账号或密码错误");
    }

    @RequestMapping(value="logout", method=RequestMethod.POST)
    @ResponseBody
    @PermessionLimit(limit=false)
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
        if (PermissionInterceptor.ifLogin(request)) {
            PermissionInterceptor.logout(request, response);
        }
        return ReturnT.SUCCESS;
    }

    @RequestMapping("/help")
    @PermessionLimit
    public String help() {
        return "help";
    }
}
