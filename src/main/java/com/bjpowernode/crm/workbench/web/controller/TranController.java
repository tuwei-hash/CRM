package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.HandleFlag;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ContactsService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/transaction")
public class TranController {

    @Autowired
    UserService userService;

    @Autowired
    ContactsService contactsService;

    @Autowired
    TranService tranService;

    @RequestMapping("/toIndex.do")
    public String toTranIndex(){

        return "/workbench/transaction/index";

    }

    @RequestMapping("/toSaveTran.do")
    @ResponseBody
    public ModelAndView toSaveTran(){

        List<User> uList = userService.getUserList();

        ModelAndView mav = new ModelAndView();

        mav.addObject("uList", uList);
        mav.setViewName("/workbench/transaction/save");

        return mav;

    }

    @RequestMapping("/getContactsListByFullName.do")
    @ResponseBody
    public List<Contacts> getContactsListByFullName(String cName){

        return contactsService.getContactsListByFullName(cName);

    }

    @RequestMapping("/getCustomerNameListByName.do")
    @ResponseBody
    public List<Customer> getCustomerNameListByName(String name){

        return contactsService.getCustomerNameListByName(name);

    }

    @RequestMapping("/saveTran.do")
    public String saveTran(Tran t, String customerName, HttpSession session){

        t.setId(UUIDUtil.getUUID());
        t.setCreateTime(DateTimeUtil.getSysTime());
        t.setCreateBy(((User)session.getAttribute("user")).getName());

        tranService.saveTranForm(t,customerName);

        return "redirect:/workbench/transaction/toIndex.do";

    }

    @RequestMapping("/toTranDetail.do")
    @ResponseBody
    public ModelAndView toTranDetail(String id, HttpServletRequest request){

        Tran tran = tranService.getTranById(id);

        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");

        String stage = tran.getStage();

        String possibility = pMap.get(stage);

        tran.setPossibility(possibility);

        ModelAndView mav = new ModelAndView();

        mav.addObject("t",tran);
        mav.setViewName("/workbench/transaction/detail");

        return mav;

    }

    @RequestMapping("/getHistoryListByTranId.do")
    @ResponseBody
    public List<Tran> getHistoryListByTranId(String tranId){

        return tranService.getHistoryListByTranId(tranId);

    }

    @RequestMapping("/changeStage.do")
    @ResponseBody
    public Map<String,Object> changeStage(Tran t, HttpServletRequest request){

        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();

        t.setEditBy(editBy);
        t.setEditTime(editTime);

        tranService.updateStage(t);

        //处理可能性
        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        t.setPossibility(pMap.get(t.getStage()));

        return HandleFlag.successObj("t",t);

    }

}
