package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/settings/dictionary")
public class DicController {

    @Autowired
    private DicService dicService;

    @RequestMapping("/toIndex.do")
    public String toIndex(){

        return "/settings/dictionary/index";
    }

    @RequestMapping("/type/toIndex.do")
    public ModelAndView toTypeIndex(){

        List<DicType> dtList=dicService.getDicTypeList();

        ModelAndView mav=new ModelAndView();

        mav.addObject("dtList",dtList);
        mav.setViewName("/settings/dictionary/type/index");

        return mav;
    }

    @RequestMapping("/type/toSave.do")
    public String toSaveType(){

        return "/settings/dictionary/type/save";
    }

    @RequestMapping("/type/checkTypeCode.do")
    @ResponseBody
    public Map<String,Object> checkTypeCode(String code){

        Map<String,Object> map=new HashMap();

        boolean flag=dicService.checkTypeCode(code);

        map.put("success",flag);

        return map ;
    }

    @RequestMapping("/type/saveType.do")
    public String saveType(DicType dicType){

        dicService.saveType(dicType);
        return "redirect:/settings/dictionary/type/toIndex.do";
    }

    @RequestMapping("/type/toEdit.do")
    public ModelAndView toEditType(String code){

        DicType dicType=dicService.selectByCode(code);

        ModelAndView mav=new ModelAndView();
        mav.addObject("dicType",dicType);
        mav.setViewName("/settings/dictionary/type/edit");

        return mav;
    }

    @RequestMapping("/type/editType.do")
    public String editType(DicType dicType){

        dicService.editType(dicType);
        return "redirect:/settings/dictionary/type/toIndex.do";
    }

    @RequestMapping("/type/deleteType.do")
    public String deleteType(String[] codes){

        dicService.deleteType(codes);

        return "redirect:/settings/dictionary/type/toIndex.do";
    }

    @RequestMapping("/value/toIndex.do")
    public ModelAndView toValueIndex(){

        List<DicValue> dvList=dicService.getDicValueList();

        ModelAndView mav=new ModelAndView();

        mav.addObject("dvList",dvList);
        mav.setViewName("/settings/dictionary/value/index");

        return mav;
    }

    @RequestMapping("/value/toSaveValue.do")
    public ModelAndView toSaveValue(){

        List<DicType> dtList=dicService.getDicTypeList();

        ModelAndView mav = new ModelAndView();

        mav.addObject("dtList",dtList);
        mav.setViewName("/settings/dictionary/value/save");

        return mav;

    }

    @RequestMapping("/value/saveValue.do")
    public String saveValue(DicValue dicValue){

        dicService.saveValue(dicValue);

        return "redirect:/settings/dictionary/value/toIndex.do";

    }

    @RequestMapping("/value/toEditValue.do")
    public ModelAndView toEditValue(String id){

        DicValue dicValue=dicService.toEditValue(id);

        ModelAndView mav = new ModelAndView();

        mav.addObject("dicValue",dicValue);
        mav.setViewName("/settings/dictionary/value/edit");

        return mav;

    }

    @RequestMapping("/value/editValue.do")
    public String editValue(DicValue dicValue){

        dicService.editValue(dicValue);

        return "redirect:/settings/dictionary/value/toIndex.do";

    }

    @RequestMapping("/value/deleteValueByIds")
    public String deleteValueByIds(String[] ids){

        dicService.deleteValueByIds(ids);

        return "redirect:/settings/dictionary/value/toIndex.do";

    }

}
