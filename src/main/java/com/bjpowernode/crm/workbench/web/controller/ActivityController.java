package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.HandleFlag;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.utils.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    UserService userService;

    @Autowired
    ActivityRemarkService remarkService;

    @RequestMapping("/toIndex.do")
    public ModelAndView toActivityIndex(){

        List<User> userList=userService.getUserList();

        ModelAndView mav = new ModelAndView();

        mav.addObject("userList",userList);
        mav.setViewName("/workbench/activity/index");

        return mav;
    }

    @RequestMapping("/getPageList.do")
    @ResponseBody
    public PaginationVo<Activity> getPageList(int pageNo, int pageSize,
                                      String owner, String name, String startDate, String endDate){

        pageNo=(pageNo-1)*pageSize;

        Map<String,Object> map=new HashMap<>();

        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("owner",owner);
        map.put("name",name);
        map.put("startDate",startDate);
        map.put("endDate",endDate);

        PaginationVo<Activity> vo=activityService.getPageList(map);

        return vo;
    }

    @RequestMapping("/saveActivity.do")
    @ResponseBody
    public Map<String,Object> saveActivity(Activity activity, HttpSession session){

        activity.setId(UUIDUtil.getUUID());

        String createBy=((User)session.getAttribute("user")).getName();

        String createTime=DateTimeUtil.getSysTime();

        activity.setCreateBy(createBy);
        activity.setCreateTime(createTime);

        activityService.saveActivity(activity);

        return HandleFlag.successTrue();
    }

    @RequestMapping("/toEditActivity.do")
    @ResponseBody
    public Activity toEditActivity(String id){

        Activity a=activityService.getActivityById(id);

        return a;
    }

    @RequestMapping("/editActivity.do")
    @ResponseBody
    public Map<String,Object> editActivity(Activity a,HttpSession session){

        String editBy=((User)session.getAttribute("user")).getName();
        String editTime=DateTimeUtil.getSysTime();

        a.setEditBy(editBy);
        a.setEditTime(editTime);

        activityService.updateActivity(a);

        return HandleFlag.successTrue();
    }

    @RequestMapping("/deleteActivityByIds.do")
    @ResponseBody
    public  Map<String,Object> deleteActivityByIds(String[] ids){

        activityService.deleteActivityByIds(ids);

        return HandleFlag.successTrue();

    }

    @RequestMapping("/exportActivityAll.do")
    public void exportActivityAll(HttpServletResponse response) throws IOException {

        List<Activity> aList=activityService.getActivityList();

        //创建了一份Excel文件
        HSSFWorkbook workBook=new HSSFWorkbook();

        //创建了文件中的一页
        HSSFSheet sheet=workBook.createSheet();

        //表格第一行
        HSSFRow row=sheet.createRow(0);

        //创建一个承载数据的数组
        HSSFCell [] cells =new HSSFCell[11];
        //将第一行每个单元格赋值给数组，动态初始化数组
        for (int i=0;i<cells.length;i++){

            cells[i]=row.createCell(i);

        }
        //给第一行填充数据
        cells[0].setCellValue("id");
        cells[1].setCellValue("owner");
        cells[2].setCellValue("name");
        cells[3].setCellValue("startDate");
        cells[4].setCellValue("endDate");
        cells[5].setCellValue("cost");
        cells[6].setCellValue("description");
        cells[7].setCellValue("createTime");
        cells[8].setCellValue("createBy");
        cells[9].setCellValue("editTime");
        cells[10].setCellValue("editBy");


        for (int i=0;i<aList.size();i++) {

            //表格主体第i行
            row=sheet.createRow(i+1);
            //将第i行每个单元格赋值给数组，动态化初始数组
            for (int j=0;j<cells.length;j++){

                cells[j]=row.createCell(j);

            }
            //获取第i行数据
            Activity a=aList.get(i);
            //给第i行单元格填充数据
            cells[0].setCellValue(a.getId());
            cells[1].setCellValue(a.getOwner());
            cells[2].setCellValue(a.getName());
            cells[3].setCellValue(a.getStartDate());
            cells[4].setCellValue(a.getEndDate());
            cells[5].setCellValue(a.getCost());
            cells[6].setCellValue(a.getDescription());
            cells[7].setCellValue(a.getCreateTime());
            cells[8].setCellValue(a.getCreateBy());
            cells[9].setCellValue(a.getEditTime());
            cells[10].setCellValue(a.getEditBy());
        }

        response.setContentType("octets/stream");
        response.setHeader("Content-Disposition","attachment;filename=Activity-"+DateTimeUtil.getSysTimeForUpload()+".xsl");

        OutputStream out=response.getOutputStream();
        workBook.write(out);

    }

    @RequestMapping("/exportActivityByIds.do")
    public void exportActivityByIds(String[] ids,HttpServletResponse response) throws IOException {

        List<Activity> aList=activityService.getActivityByIds(ids);

        //创建了一份Excel文件
        HSSFWorkbook workBook=new HSSFWorkbook();

        //创建了文件中的一页
        HSSFSheet sheet=workBook.createSheet();

        //表格第一行，表头
        HSSFRow row=sheet.createRow(0);

        //创建一个承载数据的数组
        HSSFCell [] cells=new HSSFCell[11];
        //将第一行的每个单元格赋值给数组，动态初始化数组
        for (int i=0;i<cells.length;i++){

            cells[i]=row.createCell(i);

        }

        //给第一行单元格填充数据
        cells[0].setCellValue("id");
        cells[1].setCellValue("owner");
        cells[2].setCellValue("name");
        cells[3].setCellValue("startDate");
        cells[4].setCellValue("endDate");
        cells[5].setCellValue("cost");
        cells[6].setCellValue("description");
        cells[7].setCellValue("createTime");
        cells[8].setCellValue("createBy");
        cells[9].setCellValue("editTime");
        cells[10].setCellValue("editBy");

        //表格主体
        for (int i=0;i<aList.size();i++) {

            //表格主体第i行
            row=sheet.createRow(i+1);

            //将第i行的每个单元格赋值给数组，动态初始化数组
            for (int j=0;j<cells.length;j++){

                cells[i]=row.createCell(j);

            }

            //获取第i行数据
            Activity a=aList.get(i);
            //给第i行单元格填充数据
            cells[0].setCellValue(a.getId());
            cells[1].setCellValue(a.getOwner());
            cells[2].setCellValue(a.getName());
            cells[3].setCellValue(a.getStartDate());
            cells[4].setCellValue(a.getEndDate());
            cells[5].setCellValue(a.getCost());
            cells[6].setCellValue(a.getDescription());
            cells[7].setCellValue(a.getCreateTime());
            cells[8].setCellValue(a.getCreateBy());
            cells[9].setCellValue(a.getEditTime());
            cells[10].setCellValue(a.getEditBy());
        }

        response.setContentType("octets/stream");
        response.setHeader("Content-Disposition","attachment;filename=Activity-"+DateTimeUtil.getSysTimeForUpload()+".xls");

        OutputStream out=response.getOutputStream();

        workBook.write(out);

    }

    @RequestMapping("/importActivity.do")
    @ResponseBody
    public Map<String, Object> importActivity(MultipartFile file, HttpServletRequest request) throws IOException {

        String fileName = DateTimeUtil.getSysTimeForUpload() + ".xls";

        String path = request.getServletContext().getRealPath("/temDir");

        file.transferTo(new File(path + "/" + fileName));

        InputStream input = new FileInputStream(path + "/" +fileName);

        HSSFWorkbook workbook = new HSSFWorkbook(input);

        HSSFSheet sheet = workbook.getSheetAt(0);

        List<Activity> aList = new ArrayList<>();

        for(int i=1;i<sheet.getLastRowNum()+1;i++){

            HSSFRow row = sheet.getRow(i);

            Activity a = new Activity();

            for(int j=0;j<row.getLastCellNum();j++){

                HSSFCell cell = row.getCell(j);

                String value = cell.getStringCellValue();

                if(j==0){

                    a.setId(value);

                }else if(j==1){

                    a.setOwner(value);

                }else if(j==2){

                    a.setName(value);

                }else if(j==3){

                    a.setStartDate(value);

                }else if(j==4){

                    a.setEndDate(value);

                }else if(j==5){

                    a.setCost(value);

                }else if(j==6){

                    a.setDescription(value);

                }else if(j==7){

                    a.setCreateTime(value);

                }else if(j==8){

                    a.setCreateBy(value);

                }else if(j==9){

                    a.setEditTime(value);

                }else if(j==10){

                    a.setEditBy(value);

                }

            }

            aList.add(a);
        }

        activityService.saveActivityList(aList);

        return HandleFlag.successTrue();

    }

    @RequestMapping("/toDetailActivity.do")
    public ModelAndView toDetailActivity(String id){

        Activity a=activityService.getActivityByIdForOwner(id);

        ModelAndView mav=new ModelAndView();

        mav.addObject("a",a);
        mav.setViewName("/workbench/activity/detail");

        return mav;

    }

    @RequestMapping("/getRemarkListByAid.do")
    @ResponseBody
    public List<ActivityRemark> getRemarkListByAid(String aId){

        return remarkService.getRemarkListByAid(aId);

    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Map<String,Object> updateRemark(ActivityRemark ar,HttpSession session){

        String editBy = ((User)(session.getAttribute("user"))).getName();
        String editTime = DateTimeUtil.getSysTime();

        ar.setEditBy(editBy);
        ar.setEditTime(editTime);
        ar.setEditFlag("1");

        remarkService.updateRemark(ar);

        return HandleFlag.successObj("ar",ar);

    }

    @RequestMapping("/deleteRemarkById.do")
    @ResponseBody
    public Map<String,Object> deleteRemarkById(String id){

        remarkService.deleteRemarkById(id);

        return HandleFlag.successTrue();

    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public Map<String,Object> saveRemark(ActivityRemark ar,HttpSession session){

        String id=UUIDUtil.getUUID();
        String createBy = ((User)(session.getAttribute("user"))).getName();
        String createTime = DateTimeUtil.getSysTime();

        ar.setId(id);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag("0");

        remarkService.saveRemark(ar);

        return HandleFlag.successObj("ar",ar);

    }

}
