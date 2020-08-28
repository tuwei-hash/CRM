package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/workbench/chart")
public class ChartController {

    @Autowired
    private TranService tranService;

    @RequestMapping("/transaction/toIndex.do")
    public String toTranChartIndex(){

        return "/workbench/chart/transaction/index";

    }

    @RequestMapping("/transaction/getChartData.do")
    @ResponseBody
    public Map<String,Object> getChartData(){

        Map<String, Object> map = tranService.getChartData();

        return map;

    }

}
