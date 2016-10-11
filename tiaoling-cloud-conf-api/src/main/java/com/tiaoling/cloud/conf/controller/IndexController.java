package com.tiaoling.cloud.conf.controller;

import com.tiaoling.cloud.conf.utils.ResultUtil;
import com.tiaoling.cloud.core.utils.HttpUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by yhl on 2016/10/9.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @RequestMapping(value = "login",method = {RequestMethod.POST})
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
        int size = request.getContentLength();
        InputStream is;
        try {
            is = request.getInputStream();
            byte[] reqBodyBytes = HttpUtils.readBytes(is, size);
            String params = new String(reqBodyBytes);
            logger.info("tl-conf登录（index/Login）请求报文：" + params);
            JSONObject paramJson = JSONObject.fromObject(params);
            ArrayList<String> paramNames = new ArrayList<String>();
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.creComErrorResult(e.getMessage());
        }

        return "SUCCESS";
    }
}
