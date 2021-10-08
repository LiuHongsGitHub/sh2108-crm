package com.bjpowernode.crm.base.bean;

import com.bjpowernode.crm.base.mapper.DicTypeMapper;
import com.bjpowernode.crm.base.mapper.DicValueMapper;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.base.util.RedisUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.*;

/*
* 设置一个缓冲实体类
* 在服务器启动时，所有此包下面的实体类对象就会初始化出来
* 用来初始化一些不常改动的数据，如：下拉框下的选项数据，所有者owner、线索来源（状态）等，
* 这些初始化的数据就缓存在servletContext对象中
* 4个注解：controller,service,responsity,component
* 4个域对象：request,session,servletContext,pageContext
*
* */
@Component
public class CrmCache {

    @Autowired
    public ServletContext servletContext;
    @Autowired
    private DicTypeMapper dicTypeMapper;
    @Autowired
    private DicValueMapper dicValueMapper;
    @Autowired
    private UserMapper userMapper;

    //创建一个初始化方法
    @PostConstruct
    public void init(){
        //判断redis是否存在user
        Jedis jedis = RedisUtil.getJedis();
        jedis.select(1);
        Set<String> keys1 = jedis.keys("user:*");

        List<User> users = null;
        if (keys1.size() == 0){
            //说明redis中没有数据,从数据库中取出
             users = userMapper.selectAll();
            //放入redis
            RedisUtil.dbToRedis(jedis, 1, "user:", users);
        }else {
            //有数据,从redis取出
            users = RedisUtil.redisToBean(jedis, 1, "user:*", User.class);

        }
        //将所有者既用户的对象查询出来进行缓存
        servletContext.setAttribute("users", users);
        //接收缓存数据的map集合
        Map<String, List<DicValue>> map = new HashMap<>();
        //找出类型
        Set<String> keysDicType = jedis.keys("dicType:*");
        if (keysDicType.size() == 0){
            //redis中不存在类型
            //1.所有的下拉框的类型
            List<DicType> dicTypes = dicTypeMapper.selectAll();
            //放入redis
            RedisUtil.dbToRedis(jedis, 1, "dicType:", dicTypes);
            for (DicType dicType: dicTypes){
                //2.所有的下拉框类型对应的所有值
                //2.1有些值有自己的顺序，所以要查询出来按顺序排列
                Example example = new Example(DicValue.class);
                example.setOrderByClause("orderNo ");
                example.createCriteria().andEqualTo("typeCode", dicType.getCode());
                List<DicValue> dicValue = dicValueMapper.selectByExample(example);
                //放入redis
                RedisUtil.dbToRedis(jedis, 1, "dicValue:", dicValue);
                //将要缓存的数据放入集合
                map.put(dicType.getCode(), dicValue);
            }
        }else {
            //存在，在redis中取得map
            List<DicType> dicTypes = RedisUtil.redisToBean(jedis, 1, "dicType:*", DicType.class);
            List<DicValue> dicValues1 = RedisUtil.redisToBean(jedis, 1, "dicValue:*", DicValue.class);
            for (DicType dicType:dicTypes){
                String code = dicType.getCode();
                List<DicValue> dicValues = new ArrayList<>();
                for (DicValue dicValue: dicValues1){
                    //将存在关联关系的类型和值进行关联
                    if (code.equals(dicValue.getTypeCode())){
                        dicValues.add(dicValue);
                    }
                }
                map.put(code,dicValues );
            }
        }
        //将map放入域对象中
        servletContext.setAttribute("map", map);
        //缓冲阶段和可能性 参数：文件的路径名，路径间隔使用.文件后缀名不要：该类只读取properites
        ResourceBundle bundle = ResourceBundle.getBundle("mybatis.Stage2Possibility");
        //取得所有的key值，也就是阶段名
        Enumeration<String> keys = bundle.getKeys();
        Map<String,String> stage2Possibility = new TreeMap<>();
        //遍历阶段名枚举
        for (;keys.hasMoreElements();){
            //取得每个key
            String stage = keys.nextElement();
            //取得可能性 值
            String possibility = bundle.getString(stage);
            //存储
            stage2Possibility.put(stage, possibility);
        }
        //放入缓存区
        servletContext.setAttribute("stage2Possibility", stage2Possibility);

    }
}
