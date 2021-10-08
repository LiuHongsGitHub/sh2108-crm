package com.bjpowernode.crm.test;

import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

public class TestCrm {

    ApplicationContext aa = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
    User user = new User();
    UserMapper userMapper = (UserMapper)aa.getBean("userMapper");
    Example example = new Example(User.class);
    //测试添加方法
    @Test
    public void test01(){
        user.setId(UUIDUtil.creatUUID());
        user.setName("张太虚");
        userMapper.insertSelective(user);
        System.out.println();
    }

    //测试主键
    @Test
    public void test02(){
        String s = UUIDUtil.creatUUID();
        System.out.println(s);
    }

    //测试更新
    @Test
    public void test03(){
        user.setId("123233213231");
        user.setLoginAct("momodddd");
        /*userMapper.updateByPrimaryKeySelective(user);*/
        User user2 = new User();
        user2.setLoginAct("太虚真人");

        //第一个是实体类对应的属性名，第二个是实际的参数
        example.createCriteria().andEqualTo("id", "3939a27321974240a4da86148213ab72");
        userMapper.updateByExampleSelective(user2,example);

    }

    //测试删除
    @Test
    public void test04(){

        example.createCriteria().andLike("name", "%" + "黑" + "%");
        userMapper.deleteByExample(example);
    }

    //测试查询
    @Test
    public void test05(){
        List<String> aa = new ArrayList<>();
        aa.add("123233213231");
        aa.add("123");
        example.createCriteria().andIn("id",aa );
        /*List<User> select = userMapper.select(user);
        System.out.println("select" + select);
        */
        //查询主键

        List<User> users = userMapper.selectByExample(example);
        for (User ss:users){

            System.out.println(ss);
        }
    }

}
