package com.bjpowernode.crm.base.Exception;

public class CrmException extends RuntimeException {
    private CrmEnum crmEnum;

    public CrmException(CrmEnum crmEnum) {
       //调用父类工作方法 把枚举的定义错误提示信息放入到堆栈 中
        super(crmEnum.getMessage());
        this.crmEnum = crmEnum;
    }
}
