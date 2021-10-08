package com.bjpowernode.crm.base.Exception;

public enum CrmEnum   {
    USER_LOGIN_CODE("001-001-001","验证码输入错误！"),
    USER_LOGIN_LOGINACT("001-001-002","用户名或密码错误！"),
    USER_LOGIN_EXPIRE("001-001-002","账号失效，请联系管理员！"),
    USER_LOGIN_LOCKED("001-001-004","账号被锁定，请联系管理员"),
    USER_LOGIN_IP("001-001-005","不允许登录的ip地址，请联系管理员"),
    USER_UPDATE_VERIFYPWD("001-001-006","原始密码输入错误"),
    USER_UPDATE_SUFFIX("001-002-007","只允许上传png,webp,gif,bmp后缀名的文件"),
    USER_UPDATE_MAXSIZE("001-002-008","上传头像不能超过2M"),
    USER_UPDATE_INFO("001-002-009","更新用户信息失败！"),
    ACTIVITY_SAVE_INFO("002-001-001","创建市场活动失败！"),
    ACTIVITY_UPDATE_INFO("002-001-002","修改市场活动失败！"),
    ACTIVITY_DELETE_INFO("002-001-003","删除市场活动错误！"),
    ACTIVITYREMARK_SAVE_INFO("002-002-004","保存市场活动备注错误！"),
    ACTIVITYREMARK_DELETE_INFO("002-002-004","删除市场活动备注错误！"),
    ACTIVITYREMARK_UPDATE_INFO("002-002-005","更新市场活动备注错误！"),
    CUSTOMER_UPDATE_INFO("003-001-001","更新客户信息错误！"),
    CUSTOMER_SAVE_INFO("003-001-002","保存客户信息失败！"),
    CUSTOMER_DELETE_INFO("003-001-003","删除客户信息失败！"),
    CUSTOMERREMARK_SAVE_INFO("003-002-004","保存客户备注失败！"),
    CUSTOMERREMARK_UPDATE_INFO("003-002-005","更新客户备注失败！"),
    CUSTOMERREMARK_DELETE_INFO("003-002-006","删除客户备注失败！"),
    CONTACT_SAVE_INFO("004-001-001","保存联系人失败！"),
    CONTACT_UPDATE_INFO("004-001-002","更新联系人失败！"),
    CONTACT_DELETE_INFO("004-001-003","删除联系人失败！"),
    CONTACTREMARK_SAVE_INFO("004-002-004","保存联系人备注失败！"),
    CONTACTREMARK_UPDATE_INFO("004-002-005","更新联系人备注失败！"),
    CONTACTREMARK_DELETE_INFO("004-002-006","删除联系人备注失败！"),
    CLUE_SAVE_INFO("005-001-001","保存线索失败！"),
    CLUE_UPDATE_INFO("004-001-002","更新线索失败！"),
    CLUE_DELETE_INFO("004-001-003","删除线索失败！"),
    CLUEREMARK_SAVE_INFO("004-002-004","保存线索备注失败！"),
    CLUEREMARK_UPDATE_INFO("004-002-005","更新线索备注失败！"),
    CLUEREMARK_DELETE_INFO("004-002-006","删除线索备注失败！"),
    CLUEACTIVITYRELATION_INSERT_INFO("005-001-001","关联市场活动失败！"),
    CLUEACTIVITYRELATION_DELETE_INFO("005-001-002","关联市场活动失败！"),
    CLUE_CONVERT("006-001-001","线索转换失败！"),
    TRANHISTORY_SAVE_INFO("007-001-001","添加交易历史失败！")
    ;



    private String  code;//错误代码
    private String message ;//错误信息


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

     CrmEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
