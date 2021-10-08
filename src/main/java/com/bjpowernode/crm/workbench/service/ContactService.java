package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Contact;
import com.bjpowernode.crm.workbench.bean.ContactRemark;

import java.util.List;

public interface ContactService {
    List<Contact> selectAll(int page, int pageSize, Contact contact);

    ResultVo saveOrUpate(Contact contact, User currentUser);

    Contact queryContact(String id);

    void deleteBatch(String ids);

    Contact queryDetail(String id);

    void saveRemark(ContactRemark contactRemark, User currentUser);

    void updateRemark(ContactRemark contactRemark, User currentUser);

    void deleteRemark(String remarkId);
}
