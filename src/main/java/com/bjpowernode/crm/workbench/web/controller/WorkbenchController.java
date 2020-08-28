package com.bjpowernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkbenchController {

    @RequestMapping("/workbench/toIndex.do")
    public String toIndex(){

        return "/workbench/index";

    }

    @RequestMapping("/workbench/main/toIndex.do")
    public String toMainIndex(){

        return "/workbench/main/index";

    }
}
