package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PaginationVo;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    UserDao userDao;

    @Autowired
    ActivityDao activityDao;

    //线索相关表
    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueActivityRelationDao carDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;

    //客户相关表
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;

    //联系人相关表
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;

    //交易相关表
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Override
    public List<User> getUserList() {

        return userDao.selectAllUser();

    }

    @Override
    public PaginationVo<Clue> getClueListByCondition(Map map) {

        List<Clue> cList = clueDao.selectAllByCondition(map);

        int total = clueDao.selectCountByCondition(map);

        PaginationVo<Clue> vo = new PaginationVo<>();

        vo.setDataList(cList);
        vo.setTotal(total);

        return vo;

    }

    @Override
    public Clue getClueById(String id) {

        return clueDao.selectClueById(id);

    }

    @Override
    public List<Activity> getActivityListByClueId(String id) {

        return activityDao.selectActivityByClueId(id);

    }

    @Override
    public List<Activity> getActivityListByNameNotBindClue(Map map) {

        return carDao.selectActivityByNameNotBindClue(map);

    }

    @Override
    public void saveClueActivityRelationList(String clueId, String[] aId) {

        List<ClueActivityRelation> carList = new ArrayList<>();

        for (String id : aId){

            ClueActivityRelation car = new ClueActivityRelation();

            car.setId(UUIDUtil.getUUID());
            car.setClueId(clueId);
            car.setActivityId(id);

            carList.add(car);

        }

        carDao.insertRelationList(carList);

    }

    @Override
    public void deleteClueActivityRelationById(String id) {

        carDao.deleteRelationById(id);

    }

    @Override
    public List<Activity> getActivityListByName(String aName) {

        return activityDao.selectActivityByName(aName);

    }

    @Override
    public void convert(String clueId, Tran t, String flag, String createBy){

        String createTime = DateTimeUtil.getSysTime();

        Clue c = clueDao.selectClueById(clueId);

        String company = c.getCompany();

        Customer customer = customerDao.selectCustomerByName(company);

        if (customer == null){

            customer = new Customer();

            customer.setId(UUIDUtil.getUUID());
            customer.setCreateTime(createTime);
            customer.setCreateBy(createBy);
            customer.setWebsite(c.getWebsite());
            customer.setPhone(c.getPhone());
            customer.setOwner(c.getOwner());
            customer.setNextContactTime(c.getNextContactTime());
            customer.setDescription(c.getDescription());
            customer.setName(company);
            customer.setContactSummary(c.getContactSummary());
            customer.setAddress(c.getAddress());

            customerDao.insertCustomer(customer);

        }

        Contacts contacts = new Contacts();

        contacts.setId(UUIDUtil.getUUID());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setSource(c.getSource());
        contacts.setOwner(c.getOwner());
        contacts.setNextContactTime(c.getNextContactTime());
        contacts.setmPhone(c.getmPhone());
        contacts.setJob(c.getJob());
        contacts.setFullName(c.getFullName());
        contacts.setEmail(c.getEmail());
        contacts.setDescription(c.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setContactSummary(c.getContactSummary());
        contacts.setAppellation(c.getAppellation());
        contacts.setAddress(c.getAddress());

        contactsDao.insertContacts(contacts);

        List<ClueRemark> clueRemarkList = clueRemarkDao.selectClueRemarkByClueId(clueId);

        for(ClueRemark clueRemark : clueRemarkList){

            //取得备注信息
            String noteContent = clueRemark.getNoteContent();

            //创建并添加客户备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setNoteContent(noteContent);
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            customerRemarkDao.insertCustomerRemark(customerRemark);

            //创建并添加联系人备注
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(contacts.getId());
            contactsRemarkDao.insertContactsRemark(contactsRemark);

        }

        List<ClueActivityRelation> carList = carDao.selectRelationByClueId(clueId);

        for(ClueActivityRelation car : carList){

            String activityId = car.getActivityId();

            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();

            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());

            contactsActivityRelationDao.insertRelation(contactsActivityRelation);

        }

        if ("a".equals(flag)){

            t.setOwner(c.getOwner());
            t.setNextContactTime(c.getNextContactTime());
            t.setCustomerId(customer.getId());
            t.setContactSummary(c.getContactSummary());
            t.setContactsId(contacts.getId());

            //添加交易
            tranDao.insertTran(t);

            //7.每一次添加交易（或者是更新交易阶段）后，我们都需要记录一下交易历史
            TranHistory th = new TranHistory();
            th.setId(UUIDUtil.getUUID());
            th.setTranId(t.getId());
            th.setStage(t.getStage());
            th.setMoney(t.getMoney());
            th.setExpectedDate(t.getExpectedDate());
            th.setCreateTime(createTime);
            th.setCreateBy(createBy);

            //添加交易历史
            tranHistoryDao.insertHistory(th);

        }


        for(ClueRemark clueRemark : clueRemarkList){

            clueRemarkDao.deleteClueRemarkById(clueRemark.getId());

        }

        for (ClueActivityRelation clueActivityRelation : carList){

            carDao.deleteRelationById(clueActivityRelation.getId());

        }

        clueDao.deleteClueById(clueId);

    }

}
