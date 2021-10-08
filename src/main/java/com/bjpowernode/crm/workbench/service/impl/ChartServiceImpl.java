package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.base.bean.BarVo;
import com.bjpowernode.crm.base.bean.PieVo;
import com.bjpowernode.crm.workbench.bean.Tran;
import com.bjpowernode.crm.workbench.mapper.ChartMapper;
import com.bjpowernode.crm.workbench.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {
    @Autowired
    private ChartMapper chartMapper;
    @Override
    public BarVo queryBarVo() {
        System.out.println("进入了service层");
        List<Tran> trans = chartMapper.barCharts();
        BarVo barVo = new BarVo();
        //定义一个集合接收阶段字符串
        List<String> stages = new ArrayList<>();
        //定义一个集合接收交易数目
        List<Integer> num = new ArrayList<>();
        for (Tran tran:trans){
            stages.add(tran.getStage());
            num.add(Integer.parseInt(tran.getPossibility()));

        }
        barVo.setChartData(num);
        barVo.setTitles(stages);
        System.out.println("barvo："+ barVo);
        return barVo;
    }

    @Override
    public List<PieVo> pieEcharts() {
        List<PieVo> pieVos = chartMapper.pieEcharts();
        return pieVos;
    }
}
