package com.tiaoling.cloud.dmps.controller;

import com.tiaoling.cloud.dmps.annotation.PermessionLimit;
import com.tiaoling.cloud.dmps.domain.conf.ConfGroup;
import com.tiaoling.cloud.dmps.domain.conf.ConfNode;
import com.tiaoling.cloud.dmps.domain.job.JobGroup;
import com.tiaoling.cloud.dmps.utils.CommonPropertiesUtils;
import com.tiaoling.cloud.dmps.utils.HttpUtils;
import com.tiaoling.cloud.dmps.utils.JsonUtils;
import com.tiaoling.cloud.dmps.utils.ReturnT;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yhl on 2016/10/11.
 */
@Controller
@RequestMapping(value = "job")
public class JobController {
    @RequestMapping("/info")
    @PermessionLimit
    public String JobIndex(Model model, String znodeKey){
        List<JobGroup> list = new ArrayList<JobGroup>();
        String json ="";
        Map<String,Object> params = new HashMap<String,Object>();
        String doMethod = CommonPropertiesUtils.get("job_group");
        json = HttpUtils.doPost(CommonPropertiesUtils.get("cloud_job_api_url") + "/"
                + doMethod, JsonUtils.getJSONString(params));
        if(StringUtils.isNotBlank(json))
        {
            Map<String,Object> map = JsonUtils.toMap(json);
            if(map.get("Code").toString().equals("1") && map.get("Body")!=null&& !map.get("Body").toString().equals("null"))
            {
                list=JsonUtils.getListDTO(JsonUtils.getJSONString(map.get("Body")),JobGroup.class);
            }
        }
        model.addAttribute("JobGroupList", list);
        return "job/job.index";
    }
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        int jobGroup, String executorHandler, String filterTime) {

        String json ="";
        Map<String,Object> params = new HashMap<String,Object>();
        Map<String,Object> result = new HashMap<String,Object>();
        params.put("start",start);
        params.put("limit",length);
        params.put("jobGroup",jobGroup);
        params.put("jobName",executorHandler);
        String doMethod = CommonPropertiesUtils.get("job_list");
        json = HttpUtils.doPost(CommonPropertiesUtils.get("cloud_job_api_url") + "/"
                + doMethod, JsonUtils.getJSONString(params));
        if(StringUtils.isNotBlank(json))
        {
            Map<String,Object> map = JsonUtils.toMap(json);
            if(map.get("Code").toString().equals("1") && map.get("Body")!=null)
            {
                HashMap<String,Object> body = (HashMap<String,Object>)map.get("Body");
                if(body!=null && body.get("data")!=null)
                {
                    List<ConfNode> nodes = JsonUtils.getListDTO(JsonUtils.getJSONString(body.get("data")),ConfNode.class);
                    result.put("data",nodes);
                    result.put("recordsTotal", body.get("count").toString());		// 总记录数
                    result.put("recordsFiltered", body.get("count").toString());	// 过滤后的总记录数
                }
            }
        }
        return result;
    }
    @RequestMapping("/add")
    @ResponseBody
    public ReturnT<String> add(int jobGroup, String jobCron, String jobDesc, String author, String alarmEmail,
                               String executorAppname, String executorAddress, String executorHandler, String executorParam,
                               int glueSwitch, String glueSource, String glueRemark, String childJobKey) {
        String json ="";
        int ret=0;
        String message="";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("jobGroup",jobGroup);
        params.put("jobCron",jobCron);
        params.put("jobDesc",jobDesc);
        params.put("author",author);
        params.put("alarmEmail",alarmEmail);
        params.put("appname",executorAppname);
        params.put("address",executorAddress);
        params.put("jobName",executorHandler);
        params.put("jobParams",executorParam);
        params.put("glueSwitch",glueSwitch);
        params.put("glueSource",glueSource);
        params.put("glueRemark",glueRemark);
        params.put("childJobKey",childJobKey);
        String doMethod = CommonPropertiesUtils.get("job_add");
        json = HttpUtils.doPost(CommonPropertiesUtils.get("cloud_job_api_url") + "/"
                + doMethod, JsonUtils.getJSONString(params));
        if(StringUtils.isNotBlank(json))
        {
            Map<String,Object> map = JsonUtils.toMap(json);
            if(map.get("Code").toString().equals("1") && map.get("Body")!=null)
            {
                JSONObject object = JSONObject.fromObject(map.get("Body").toString());
                ret = object.getInt("row");
            }
            else
            {
                message=map.get("Message").toString();
            }
        }
        return (ret>0)?ReturnT.SUCCESS:new ReturnT<String>(500, message);
    }
}
