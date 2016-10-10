package com.tiaoling.cloud.conf.controller;

import com.tiaoling.cloud.conf.domain.ConfGroup;
import com.tiaoling.cloud.conf.service.intf.ConfGroupService;
import com.tiaoling.cloud.conf.utils.ResultUtil;
import com.tiaoling.cloud.core.utils.HttpUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yhl on 2016/10/10.
 */
@Controller
@RequestMapping(value = "group")
public class GroupController {
    private Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private ConfGroupService groupService;

    @RequestMapping(value = "add",method = {RequestMethod.POST},produces = "application/json; charset=utf-8")
    @ResponseBody
    public String group(HttpServletRequest request, HttpServletResponse response)
    {
        Map<String,Object> result = new HashMap<>();
        int size = request.getContentLength();
        InputStream is;
        try {
            is = request.getInputStream();
            byte[] reqBodyBytes = HttpUtils.readBytes(is, size);
            String params = new String(reqBodyBytes);
            logger.info("tl-conf新增分组（group）（group/add）请求报文：" + params);
            JSONObject paramJson = JSONObject.fromObject(params);
            if(paramJson==null)
            {
                return ResultUtil.creComErrorResult("分组信息不能为空");
            }
            if(paramJson.get("groupKey")==null || paramJson.getString("groupKey").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("分组键(Key)不能为空");
            }
            if(paramJson.get("groupName")==null || paramJson.getString("groupName").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("分组名称不能为空");
            }
            ConfGroup group= groupService.getGroup(paramJson.getString("groupName").trim());
            if(group!=null)
            {
                return ResultUtil.creComErrorResult("分组已经存在");
            }
            else
            {
                ConfGroup group1 = new ConfGroup();
                group1.setGroupKey(paramJson.getString("groupKey").trim());
                group1.setGroupName(paramJson.getString("groupName").trim());
                group1.setIsdelete(false);
                int row= groupService.addGroup(group1);
                result.put("row",row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.creObjSucResult(result);
    }
}
