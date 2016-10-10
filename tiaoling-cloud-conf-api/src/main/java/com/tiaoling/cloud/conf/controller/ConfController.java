package com.tiaoling.cloud.conf.controller;

import com.tiaoling.cloud.conf.domain.ConfGroup;
import com.tiaoling.cloud.conf.domain.ConfNode;
import com.tiaoling.cloud.conf.service.intf.ConfGroupService;
import com.tiaoling.cloud.conf.service.intf.ConfNodeService;
import com.tiaoling.cloud.conf.utils.ResultUtil;
import com.tiaoling.cloud.conf.zk.ConfZkClient;
import com.tiaoling.cloud.core.utils.HttpUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.List;
import java.util.Map;

/**
 * Created by yhl on 2016/10/9.
 */
@Controller
@RequestMapping(value = "conf")
public class ConfController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ConfGroupService groupService;
    @Autowired
    private ConfNodeService nodeService;

    @RequestMapping(value = "group",method = {RequestMethod.POST},produces = "application/json; charset=utf-8")
    @ResponseBody
    public String group(HttpServletRequest request, HttpServletResponse response)
    {
        List<ConfGroup> lists = new ArrayList<>();
        int size = request.getContentLength();
        InputStream is;
        try {
            is = request.getInputStream();
            byte[] reqBodyBytes = HttpUtils.readBytes(is, size);
            String params = new String(reqBodyBytes);
            logger.info("tl-conf获取分组（group）（conf/group）请求报文：" + params);
            JSONObject paramJson = JSONObject.fromObject(params);
            ArrayList<String> paramNames = new ArrayList<String>();
            lists=groupService.findAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        ConfGroup group = new ConfGroup();
//        group.setGroupName("group1");
//        group.setGroupTitle("group1");
//        lists.add(group);
        return ResultUtil.creObjSucResultArray(lists);
    }
    @RequestMapping(value = "node",method = {RequestMethod.POST},produces = "application/json; charset=utf-8")
    @ResponseBody
    public String node(HttpServletRequest request, HttpServletResponse response)
    {
        List<ConfNode> lists = new ArrayList<ConfNode>();
        int count=0;
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,Object> params = new HashMap<String,Object>();
        int size = request.getContentLength();
        InputStream is;
        try {
            is = request.getInputStream();
            byte[] reqBodyBytes = HttpUtils.readBytes(is, size);
            String paramStr = new String(reqBodyBytes);
            logger.info("tl-conf获取分组结点（node）（conf/node）请求报文：" + paramStr);
            JSONObject paramJson = JSONObject.fromObject(paramStr);
            if(paramJson!=null)
            {
                params.put("start",paramJson.get("start"));
                params.put("limit",paramJson.get("limit"));
                params.put("nodeGroup",paramJson.get("nodeGroup"));
                params.put("nodeKey",paramJson.get("nodeKey"));

                lists=nodeService.pageList(params);
                if(!CollectionUtils.isEmpty(lists))
                {
                    for(ConfNode node : lists)
                    {
                        node.setGroupKey(ConfZkClient.generateGroupKey(node.getNodeGroup(),node.getNodeKey()));
                        node.setNodeValueReal(ConfZkClient.getPathDataByKey(node.getGroupKey()));
                    }
                }
                count=nodeService.count(params);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.put("data",lists);
        result.put("count",count);
        return ResultUtil.creObjSucResult(result);
    }
}
