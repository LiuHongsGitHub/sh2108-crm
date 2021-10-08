package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.*;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ClueService {
    List<Clue> list(int page, int pageSize, Clue clue);

    ResultVo saveOrUpate(Clue clue, User currentUser);

    Clue queryClue(String id);

    void deleteBatch(String ids);

    Clue queryDetail(String id);

    void saveRemark(ClueRemark clueRemark, User currentUser);

    void deleteRemark(String remarkId);

    void updateRemark(ClueRemark clueRemark, User currentUser);

    List<Activity> queryActivities(String id,String name);

    List<Activity> bind(String id, String ids);

    void unbind(ClueActivityRelation clueActivityRelation);

    List<Activity> queryBindActivities(String id, String name);

    void convert(String isCreateTransaction, Tran tran, String clueId, User user);
}
