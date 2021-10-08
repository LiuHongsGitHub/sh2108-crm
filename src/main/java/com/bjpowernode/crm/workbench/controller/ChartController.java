package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.bean.BarVo;
import com.bjpowernode.crm.base.bean.PieVo;
import com.bjpowernode.crm.workbench.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChartController {
    @Autowired
    private ChartService chartService;
    //柱状图
    @RequestMapping("/workbench/chart/barEcharts")
    public BarVo barEcharts(){
        System.out.println("进入了控制器");
        BarVo  barVo= chartService.queryBarVo();
        System.out.println("barvo: + " + barVo);
        return barVo;
    }

    //饼状图
    @RequestMapping("/workbench/chart/pieEcharts")
    public List<PieVo> pieEcharts(){
        System.out.println("进入了控制器");
        List<PieVo> pieVos= chartService.pieEcharts();
        System.out.println("barvo: + " + pieVos);
        return pieVos;
    }


}
