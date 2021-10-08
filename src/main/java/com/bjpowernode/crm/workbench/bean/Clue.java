package com.bjpowernode.crm.workbench.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "tbl_clue")
@NameStyle(Style.normal)
public class Clue {
    @Id
    private String id;
    private String fullname;//联系人
    private String appellation;//联系人
    private String owner;
    private String company;//公司名称
    private String job;//联系人
    private String email;//联系人
    private String phone;//联系人
    private String website;//公司
    private String mphone;//公司
    private String state;
    private String source;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String description;
    private String contactSummary;//联系人
    private String nextContactTime;//联系人
    private String address;//联系人
    private List<ClueRemark> clueRemarks;
    private List<Activity> activities;//关联的市场活动
}
