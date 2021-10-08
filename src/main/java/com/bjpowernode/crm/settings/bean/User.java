package com.bjpowernode.crm.settings.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
//多个单词以下划线进行分割
@NameStyle(Style.normal)
//tkMapper默认以实体类名首字母小写作为表名
@Table(name = "tbl_user")
public class User {
    @Id
    private String id;
    private String loginAct;
    private String name;
    private String loginPwd  ;
    private String email ;
    private String expireTime ;
    private String lockState ;
    private String deptno;
    private String allowIps;
    private String createTime;
    private String createBy;
    private String editTime;
    private String editBy;
    private String img ;
}
