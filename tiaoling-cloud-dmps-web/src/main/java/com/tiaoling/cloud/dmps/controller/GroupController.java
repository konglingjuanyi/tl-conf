package com.tiaoling.cloud.dmps.controller;

import com.tiaoling.cloud.dmps.domain.conf.ConfGroup;
import com.tiaoling.cloud.dmps.utils.CommonPropertiesUtils;
import com.tiaoling.cloud.dmps.utils.HttpUtils;
import com.tiaoling.cloud.dmps.utils.JsonUtils;
import com.tiaoling.cloud.dmps.utils.ReturnT;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yhl on 2016/10/10.
 */
@Controller
@RequestMapping(value = "/group")
public class GroupController {
    @RequestMapping
    public String index(Model model) {
        List<ConfGroup> list = new ArrayList<>();
        String json ="";
        Map<String,Object> params = new HashMap<String,Object>();
        String doMethod = CommonPropertiesUtils.get("group_list");
        json = HttpUtils.doPost(CommonPropertiesUtils.get("cloud_conf_api_url") + "/"
                + doMethod, JsonUtils.getJSONString(params));
        if(StringUtils.isNotBlank(json))
        {
            Map<String,Object> map = JsonUtils.toMap(json);
            if(map.get("Code").toString().equals("1") && map.get("Body")!=null)
            {
                list=JsonUtils.getListDTO(JsonUtils.getJSONString(map.get("Body")),ConfGroup.class);
            }
        }
        model.addAttribute("list", list);
        return "group/group.index";
    }
    @RequestMapping("/save")
    @ResponseBody
    public ReturnT<String> save(ConfGroup ConfGroup){
        // valid
        if (ConfGroup.getGroupKey()==null || StringUtils.isBlank(ConfGroup.getGroupKey())) {
            return new ReturnT<String>(500, "请输入GroupKey");
        }
        if (ConfGroup.getGroupKey().length()<1 || ConfGroup.getGroupKey().length()>100) {
            return new ReturnT<String>(500, "GroupKey长度限制为1~100");
        }
        if (ConfGroup.getGroupName()==null || StringUtils.isBlank(ConfGroup.getGroupName())) {
            return new ReturnT<String>(500, "请输入分组名称");
        }
        if (ConfGroup.getGroupName().length()<1 || ConfGroup.getGroupName().length()>100) {
            return new ReturnT<String>(500, "GroupName长度限制为1~100");
        }
        int ret =0;
        String json ="";
        String message="";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("groupKey",ConfGroup.getGroupKey());
        params.put("groupName",ConfGroup.getGroupName());
        String doMethod = CommonPropertiesUtils.get("group_add");
        json = HttpUtils.doPost(CommonPropertiesUtils.get("cloud_conf_api_url") + "/"
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
        return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
    }
    @RequestMapping("/update")
    @ResponseBody
    public ReturnT<String> update(ConfGroup ConfGroup){

        // valid
        if (ConfGroup.getGroupKey()==null || StringUtils.isBlank(ConfGroup.getGroupKey())) {
            return new ReturnT<String>(500, "请输入GroupKey");
        }
        if (ConfGroup.getGroupKey().length()<1 || ConfGroup.getGroupKey().length()>100) {
            return new ReturnT<String>(500, "GroupKey长度限制为1~100");
        }
        if (ConfGroup.getGroupName()==null || StringUtils.isBlank(ConfGroup.getGroupName())) {
            return new ReturnT<String>(500, "请输入分组名称");
        }
        if (ConfGroup.getGroupName().length()<1 || ConfGroup.getGroupName().length()>100) {
            return new ReturnT<String>(500, "GroupName长度限制为1~100");
        }

        int ret =0;
        String json ="";
        String message="";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("groupKey",ConfGroup.getGroupKey());
        params.put("groupName",ConfGroup.getGroupName());
        params.put("isRemove",0);
        String doMethod = CommonPropertiesUtils.get("group_update");
        json = HttpUtils.doPost(CommonPropertiesUtils.get("cloud_conf_api_url") + "/"
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
    @RequestMapping("/remove")
    @ResponseBody
    public ReturnT<String> remove(String groupKey){

        int ret =0;
        String json ="";
        String message="";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("groupKey",groupKey);
        params.put("isRemove",1);
        params.put("isdelete",1);
        String doMethod = CommonPropertiesUtils.get("group_update");
        json = HttpUtils.doPost(CommonPropertiesUtils.get("cloud_conf_api_url") + "/"
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
