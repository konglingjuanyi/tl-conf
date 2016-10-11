package com.tiaoling.cloud.job.controller;

import com.tiaoling.cloud.core.utils.HttpUtils;
import com.tiaoling.cloud.job.domain.JobDetail;
import com.tiaoling.cloud.job.domain.TriggerGroup;
import com.tiaoling.cloud.job.service.intf.JobService;
import com.tiaoling.cloud.job.utils.ResultUtil;
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
import java.util.List;

/**
 * Created by yhl on 2016/10/11.
 */
@Controller
@RequestMapping("job")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "info",method = {RequestMethod.POST},produces = "application/json; charset=utf-8")
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
        if(lists==null || lists.size()==0) return ResultUtil.creObjSucResult(lists);
        return ResultUtil.creObjSucResultArray(lists);
    }
}
