package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.StageVo;
import com.bjpowernode.crm.workbench.bean.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<String> queryCustomerName(String customerName);

    List<Tran> list(Tran tran, int page, int pageSize);

    Map<String, Object> queryStage(Integer index,String id, Map<String,String> stage2Possibility, User currentUser);
}
