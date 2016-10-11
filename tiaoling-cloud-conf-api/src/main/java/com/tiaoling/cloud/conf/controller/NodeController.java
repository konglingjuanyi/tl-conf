package com.tiaoling.cloud.conf.controller;

import com.tiaoling.cloud.conf.domain.ConfGroup;
import com.tiaoling.cloud.conf.domain.ConfNode;
import com.tiaoling.cloud.conf.service.intf.ConfNodeService;
import com.tiaoling.cloud.conf.utils.ResultUtil;
import com.tiaoling.cloud.conf.zk.ConfZkClient;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yhl on 2016/10/11.
 */
@Controller
@RequestMapping(value = "node")
public class NodeController {
    private static Logger logger = LoggerFactory.getLogger(NodeController.class);

    @Autowired
    private ConfNodeService nodeService;

    @RequestMapping(value = "add",method = {RequestMethod.POST},produces = "application/json; charset=utf-8")
    @ResponseBody
    public String add(HttpServletRequest request, HttpServletResponse response)
    {
        Map<String,Object> result = new HashMap<>();
        int row =0;
        int size = request.getContentLength();
        InputStream is;
        try {
            is = request.getInputStream();
            byte[] reqBodyBytes = HttpUtils.readBytes(is, size);
            String params = new String(reqBodyBytes);
            logger.info("tl-conf新增配置结点（node）（node/add）请求报文：" + params);
            JSONObject paramJson = JSONObject.fromObject(params);
            if(paramJson==null)
            {
                return ResultUtil.creComErrorResult("配置结点信息不能为空");
            }
            if(paramJson.get("nodeGroup")==null || paramJson.getString("nodeGroup").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("分组键(Key)不能为空");
            }
            if(paramJson.get("nodeKey")==null || paramJson.getString("nodeKey").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("配置结点Key不能为空");
            }
            if(paramJson.get("nodeValue")==null || paramJson.getString("nodeValue").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("配置结点值不能为空");
            }
            if(paramJson.get("nodeDesc")==null || paramJson.getString("nodeDesc").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("配置结点描述不能为空");
            }
            ConfNode node =null;
            node = nodeService.getConfNode(paramJson.getString("nodeGroup"),paramJson.getString("nodeKey"));
            if(node!=null)
            {
                return ResultUtil.creComErrorResult("配置结点已经存在");
            }
            node=new ConfNode();
            node.setNodeGroup(paramJson.get("nodeGroup").toString());
            node.setNodeKey(paramJson.get("nodeKey").toString());
            node.setNodeValue(paramJson.get("nodeValue").toString());
            node.setNodeDesc(paramJson.get("nodeDesc").toString());
            node.setIsdelete(false);
            row = nodeService.insert(node);
            result.put("row",row);
            String groupKey = ConfZkClient.generateGroupKey(node.getNodeGroup(), node.getNodeKey());
            ConfZkClient.setPathDataByKey(groupKey, node.getNodeValue());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.creComErrorResult(e.getMessage());
        }
        return ResultUtil.creObjSucResult(result);
    }
    @RequestMapping(value = "update",method = {RequestMethod.POST},produces = "application/json; charset=utf-8")
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response)
    {
        Map<String,Object> result = new HashMap<>();
        int size = request.getContentLength();
        int row =0;
        ConfNode node=null;
        InputStream is;
        try {
            is = request.getInputStream();
            byte[] reqBodyBytes = HttpUtils.readBytes(is, size);
            String params = new String(reqBodyBytes);
            logger.info("tl-conf修改配置结点（node）（node/update）请求报文：" + params);
            JSONObject paramJson = JSONObject.fromObject(params);
            if(paramJson==null)
            {
                return ResultUtil.creComErrorResult("配置结点信息不能为空");
            }
            int isRemove =paramJson.getInt("isRemove");
            if(paramJson.get("nodeGroup")==null || paramJson.getString("nodeGroup").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("分组键(Key)不能为空");
            }
            if(paramJson.get("nodeKey")==null || paramJson.getString("nodeKey").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("配置结点键(Key)不能为空");
            }
            node = nodeService.getConfNode(paramJson.getString("nodeGroup").trim(),paramJson.getString("nodeKey").trim());
            if(node==null)
            {
                return ResultUtil.creComErrorResult("配置结点不存在");
            }
            if(isRemove==0) {
                if (paramJson.get("nodeValue") == null || paramJson.getString("nodeValue").trim().equals("")) {
                    return ResultUtil.creComErrorResult("配置结点值不能为空");
                }
                if (paramJson.get("nodeDesc") == null || paramJson.getString("nodeDesc").trim().equals("")) {
                    return ResultUtil.creComErrorResult("配置结点描述不能为空");
                }
                node.setNodeDesc(paramJson.get("nodeDesc").toString().trim());
                node.setNodeValue(paramJson.get("nodeValue").toString().trim());
            }
            else {
                node.setIsdelete(true);
            }
            row=nodeService.updateConfNode(node);
            if(isRemove==0){
                String groupKey = ConfZkClient.generateGroupKey(node.getNodeGroup(), node.getNodeKey());
                ConfZkClient.setPathDataByKey(groupKey, node.getNodeValue());
            }
            else
            {
                String groupKey = ConfZkClient.generateGroupKey(node.getNodeGroup(), node.getNodeKey());
                ConfZkClient.deletePathByKey(groupKey);
            }
            result.put("row",row);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.creComErrorResult(e.getMessage());
        }
        return ResultUtil.creObjSucResult(result);
    }
}
