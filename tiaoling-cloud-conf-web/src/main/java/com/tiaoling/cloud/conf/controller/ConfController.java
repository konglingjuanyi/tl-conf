package com.tiaoling.cloud.conf.controller;

import com.tiaoling.cloud.conf.annotation.PermessionLimit;
import com.tiaoling.cloud.conf.domain.ConfGroup;
import com.tiaoling.cloud.conf.domain.ConfNode;
import com.tiaoling.cloud.conf.utils.CommonPropertiesUtils;
import com.tiaoling.cloud.conf.utils.HttpUtils;
import com.tiaoling.cloud.conf.utils.JsonUtils;
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
 * Created by yhl on 2016/10/9.
 */
@Controller
@RequestMapping("/conf")
public class ConfController {
    @RequestMapping("")
    @PermessionLimit
    public String index(Model model, String znodeKey){
        List<ConfGroup> list = new ArrayList<ConfGroup>();
        String json ="";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("nodeKey",znodeKey);
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
        model.addAttribute("ConfGroup", list);
        return "conf/conf.index";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    @PermessionLimit
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        String nodeGroup, String nodeKey) {
        String json ="";
        Map<String,Object> params = new HashMap<String,Object>();
        Map<String,Object> result = new HashMap<String,Object>();
        params.put("start",start);
        params.put("limit",length);
        params.put("nodeGroup",nodeGroup);
        params.put("nodeKey",nodeKey);
        String doMethod = CommonPropertiesUtils.get("node_list");
        json = HttpUtils.doPost(CommonPropertiesUtils.get("cloud_conf_api_url") + "/"
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
}
