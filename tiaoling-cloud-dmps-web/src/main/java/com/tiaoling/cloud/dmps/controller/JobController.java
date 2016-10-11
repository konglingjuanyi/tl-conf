package com.tiaoling.cloud.dmps.controller;

import com.tiaoling.cloud.dmps.annotation.PermessionLimit;
import com.tiaoling.cloud.dmps.domain.conf.ConfGroup;
import com.tiaoling.cloud.dmps.domain.job.JobGroup;
import com.tiaoling.cloud.dmps.utils.CommonPropertiesUtils;
import com.tiaoling.cloud.dmps.utils.HttpUtils;
import com.tiaoling.cloud.dmps.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        String doMethod = CommonPropertiesUtils.get("job_info");
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
}
