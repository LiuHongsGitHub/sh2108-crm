package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.CustomerRemark;

import java.util.List;

public interface CustomerService {
    List<Customer> list(int page, int pageSize, Customer customer);

    ResultVo saveOrUpate(Customer customer, User currentUser);

    Customer queryCustomer(String id);
    Customer queryCustomerByName(String customerName);
    void deleteBatch(String ids);

    Customer queryDetail(String id);

    void saveRemark(CustomerRemark customerRemark, User currentUser);

    void updateRemark(CustomerRemark customerRemark, User currentUser);

    void deleteRemark(String remarkId);

}
