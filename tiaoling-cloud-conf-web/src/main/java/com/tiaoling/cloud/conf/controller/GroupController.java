package com.tiaoling.cloud.conf.controller;

import com.tiaoling.cloud.conf.domain.ConfGroup;
import com.tiaoling.cloud.conf.utils.CommonPropertiesUtils;
import com.tiaoling.cloud.conf.utils.HttpUtils;
import com.tiaoling.cloud.conf.utils.JsonUtils;
import com.tiaoling.cloud.conf.utils.ReturnT;
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
        if (ConfGroup.getGroupKey().length()<4 || ConfGroup.getGroupKey().length()>100) {
            return new ReturnT<String>(500, "GroupKey长度限制为4~100");
        }
        if (ConfGroup.getGroupName()==null || StringUtils.isBlank(ConfGroup.getGroupName())) {
            return new ReturnT<String>(500, "请输入分组名称");
        }
        String json ="";
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

            }
        }
        int ret =0;
        return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
    }
}
