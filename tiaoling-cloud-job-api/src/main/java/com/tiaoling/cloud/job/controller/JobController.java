package com.tiaoling.cloud.job.controller;

import com.tiaoling.cloud.core.utils.HttpUtils;
import com.tiaoling.cloud.job.domain.JobDetail;
import com.tiaoling.cloud.job.domain.TriggerGroup;
import com.tiaoling.cloud.job.domain.TriggerInfo;
import com.tiaoling.cloud.job.service.intf.JobService;
import com.tiaoling.cloud.job.utils.ResultUtil;
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
 * Created by yhl on 2016/10/11.
 */
@Controller
@RequestMapping("job")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "group",method = {RequestMethod.POST},produces = "application/json; charset=utf-8")
    @ResponseBody
    public String jobInfo(HttpServletRequest request, HttpServletResponse response)
    {
        List<TriggerGroup> lists = new ArrayList<>();
//        int size = request.getContentLength();
//        InputStream is;
        try {
//            is = request.getInputStream();
//            byte[] reqBodyBytes = HttpUtils.readBytes(is, size);
//            String params = new String(reqBodyBytes);
//            logger.info("tl-job获取分组（job）（job/info）请求报文：" + params);
//            JSONObject paramJson = JSONObject.fromObject(params);
//            ArrayList<String> paramNames = new ArrayList<String>();
            lists=jobService.findAllTriggerGroup();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.creComErrorResult(e.getMessage());
        }
        if(lists==null || lists.size()==0) return ResultUtil.creObjSucResult(null);
        return ResultUtil.creObjSucResultArray(lists);
    }
    @RequestMapping(value = "list",method = {RequestMethod.POST},produces = "application/json; charset=utf-8")
    @ResponseBody
    public String jobList(HttpServletRequest request, HttpServletResponse response)
    {
        Map<String,Object> result = new HashMap<>();
        List<TriggerInfo> lists = new ArrayList<>();
        Map<String,Object> params = new HashMap<>();
        int count=0;
        int size = request.getContentLength();
        InputStream is;
        try {
            is = request.getInputStream();
            byte[] reqBodyBytes = HttpUtils.readBytes(is, size);
            String paramsStr = new String(reqBodyBytes);
            logger.info("tl-job获取分组（job）（job/info）请求报文：" + paramsStr);
            JSONObject paramJson = JSONObject.fromObject(paramsStr);
            if(paramJson!=null)
            {
                params.put("start",paramJson.get("start"));
                params.put("limit",paramJson.get("limit"));
                params.put("jobGroup",paramJson.get("jobGroup"));
                params.put("jobName",paramJson.get("jobName"));

                lists=jobService.findTriggerInfoPageList(params);
                count=jobService.findTriggerInfoPageListCount(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.creComErrorResult(e.getMessage());
        }
        result.put("data",lists);
        result.put("count",count);
        return ResultUtil.creObjSucResult(result);
    }
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
            logger.info("tl-job新增TriggerInfo（job）（job/add）请求报文：" + params);
            JSONObject paramJson = JSONObject.fromObject(params);
            TriggerInfo triggerInfo = new TriggerInfo();
            if(paramJson==null)
            {
                return ResultUtil.creComErrorResult("TriggerInfo信息不能为空");
            }
            if(paramJson.get("jobGroup")==null || paramJson.getString("jobGroup").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("分组不能为空");
            }
            triggerInfo.setJobGroup(paramJson.getInt("jobGroup"));
            if(paramJson.get("jobCron")==null || paramJson.getString("jobCron").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("jobCron不能为空");
            }
            triggerInfo.setJobCron(paramJson.getString("jobCron"));
            if(paramJson.get("author")==null || paramJson.getString("author").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("author不能为空");
            }
            triggerInfo.setAuthor(paramJson.getString("author"));
            if(paramJson.get("alarmEmail")==null || paramJson.getString("alarmEmail").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("alarmEmail不能为空");
            }
            triggerInfo.setAlarmEmail(paramJson.getString("alarmEmail"));
            if(paramJson.get("appname")==null || paramJson.getString("appname").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("appname不能为空");
            }
            if(paramJson.get("address")==null || paramJson.getString("address").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("address不能为空");
            }
            if(paramJson.get("jobName")==null || paramJson.getString("jobName").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("jobName不能为空");
            }
            if(paramJson.get("jobParams")==null || paramJson.getString("jobParams").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("jobParams不能为空");
            }
            if(paramJson.get("glueSwitch")==null || paramJson.getString("glueSwitch").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("glueSwitch不能为空");
            }
            if(paramJson.get("glueSource")==null || paramJson.getString("glueSource").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("glueSource不能为空");
            }
            if(paramJson.get("glueRemark")==null || paramJson.getString("glueRemark").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("glueRemark不能为空");
            }
            if(paramJson.get("childJobKey")==null || paramJson.getString("childJobKey").trim().equals(""))
            {
                return ResultUtil.creComErrorResult("childJobKey不能为空");
            }
//            node = nodeService.getConfNode(paramJson.getString("nodeGroup"),paramJson.getString("nodeKey"));
//            if(node!=null)
//            {
//                return ResultUtil.creComErrorResult("配置结点已经存在");
//            }
//            node=new ConfNode();
//            node.setNodeGroup(paramJson.get("nodeGroup").toString());
//            node.setNodeKey(paramJson.get("nodeKey").toString());
//            node.setNodeValue(paramJson.get("nodeValue").toString());
//            node.setNodeDesc(paramJson.get("nodeDesc").toString());
//            node.setIsdelete(false);
//            row = nodeService.insert(node);
//            result.put("row",row);
//            String groupKey = ConfZkClient.generateGroupKey(node.getNodeGroup(), node.getNodeKey());
//            ConfZkClient.setPathDataByKey(groupKey, node.getNodeValue());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.creComErrorResult(e.getMessage());
        }
        return ResultUtil.creObjSucResult(result);
    }
}
