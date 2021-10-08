package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.BarVo;
import com.bjpowernode.crm.base.bean.PieVo;

import java.util.List;

public interface ChartService {


    BarVo queryBarVo();

    List<PieVo> pieEcharts();

}
