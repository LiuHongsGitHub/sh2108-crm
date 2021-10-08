package com.bjpowernode.crm.base.bean;

import lombok.Data;

import java.util.List;
@Data
public class BarVo {

    private List<String> titles;
    private List<Integer>  chartData;
}
