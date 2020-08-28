package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.HandleFlag;
import com.bjpowernode.crm.utils.PaginationVo;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    @Autowired
    ClueService clueService;

    @RequestMapping("/toIndex.do")
    public ModelAndView toClueIndex(){

        List<User> uList = clueService.getUserList();

        ModelAndView mav = new ModelAndView();

        mav.addObject("uList",uList);
        mav.setViewName("/workbench/clue/index");

        return mav;

    }

    @RequestMapping("/searchClueByCondition.do")
    @ResponseBody
    public PaginationVo<Clue> searchClueByCondition(int pageNo, int pageSize,
                                                    String fullName, String company, String phone, String owner, String mPhone){

        HashMap<Object, Object> map = new HashMap<>();

        pageNo = (pageNo-1)*pageSize;

        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("fullName",fullName);
        map.put("company",company);
        map.put("phone",phone);
        map.put("owner",owner);
        map.put("mPhone",mPhone);

        PaginationVo<Clue> vo = clueService.getClueListByCondition(map);

        return vo;

    }

    @RequestMapping("/toDetail.do")
    @ResponseBody
    public ModelAndView toClueDetail(String id){

        Clue clue = clueService.getClueById(id);

        ModelAndView mav = new ModelAndView();

        mav.addObject("c",clue);
        mav.setViewName("/workbench/clue/detail");

        return mav;

    }

    @RequestMapping("/getActivityListByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByClueId(String id){

        return clueService.getActivityListByClueId(id);

    }

    @RequestMapping("/getActivityByNameNotBindClue.do")
    @ResponseBody
    public List<Activity> getActivityByNameNotBindClue(String aName, String clueId){

        HashMap<String, Object> map = new HashMap<>();

        map.put("aName",aName);
        map.put("clueId",clueId);

        return clueService.getActivityListByNameNotBindClue(map);

    }

    @RequestMapping("/bind.do")
    @ResponseBody
    public Map<String,Object> bind(String clueId, String[] aId){

        clueService.saveClueActivityRelationList(clueId,aId);

        return HandleFlag.successTrue();

    }

    @RequestMapping("/deleteCarById.do")
    @ResponseBody
    public Map<String,Object> deleteCarById(String id){

        clueService.deleteClueActivityRelationById(id);

        return HandleFlag.successTrue();

    }

    @RequestMapping("/toConvert.do")
    @ResponseBody
    public ModelAndView toConvert(String clueId){

        Clue clue = clueService.getClueById(clueId);

        ModelAndView mav = new ModelAndView();

        mav.addObject("c",clue);
        mav.setViewName("/workbench/clue/convert");

        return mav;

    }

    @RequestMapping("/getActivityListByName.do")
    @ResponseBody
    public List<Activity> getActivityListByName(String aName){

        return clueService.getActivityListByName(aName);

    }

    @RequestMapping("/convert.do")
    public String convert(String clueId, Tran t, String flag, HttpSession session){

        String createBy = ((User)session.getAttribute("user")).getName();

        if ("a".equals(flag)){

            t.setId(UUIDUtil.getUUID());
            t.setCreateBy(createBy);
            t.setCreateTime(DateTimeUtil.getSysTime());

        }

        clueService.convert(clueId, t, flag, createBy);

        return "redirect:/workbench/clue/toIndex.do";

    }

}