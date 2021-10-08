package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.bean.ClueRemark;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.workbench.bean.CustomerRemark;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ClueRemarkMapper extends Mapper<ClueRemark> {
    List<Customer> select(CustomerRemark customerRemark);
}
