package com.bjpowernode.crm.settings.service.Impl;

import com.bjpowernode.crm.base.Exception.CrmEnum;
import com.bjpowernode.crm.base.Exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryAllUser() {
        return userMapper.selectAll();
    }

    @Override
    public User login(User user, String code, String correctCode,String ip) {
        //校验验证码是否输入正确
        if (!correctCode.equalsIgnoreCase(code)){
            //不一致,抛异常
            throw new CrmException(CrmEnum.USER_LOGIN_CODE);
        }
        //一致,用户名和密码进行判断
        String loginAct = user.getLoginAct();
        String loginPwd = user.getLoginPwd();

        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("loginAct", loginAct).andEqualTo("loginPwd", loginPwd);
        List<User> users = userMapper.selectByExample(example);
        if(users.size() == 0 ){
            //说明没有找到，不一致
            throw new CrmException(CrmEnum.USER_LOGIN_LOGINACT);
        }


        User user1 = users.get(0);
        String userExpireTime =user1.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        //判断账号是否失效
        if (userExpireTime.compareTo(sysTime) < 0 ){
            //说明无效
            throw new CrmException(CrmEnum.USER_LOGIN_EXPIRE);
        }

        //判断账号是否被锁定 0锁定，1未锁定
        if(user1.getLockState().equals("0")){
            //说明被锁定
            throw new CrmException(CrmEnum.USER_LOGIN_LOCKED);
        }

        //判断IP是否符合
        if(!user1.getAllowIps().contains(ip)){
            //不符合抛异常
            throw new CrmException(CrmEnum.USER_LOGIN_IP);
        }
        return user1 ;
    }

    @Override
    public void verify(User loginPwd, String oldPwd) {
        if (!loginPwd.getLoginPwd().equals(oldPwd)){
            throw new CrmException(CrmEnum.USER_UPDATE_VERIFYPWD);
        }
    }

    @Override
    public void updateUser(User user) {
        if(userMapper.updateByPrimaryKeySelective(user) <= 0){
            throw new CrmException(CrmEnum.USER_UPDATE_INFO);
        }
    }

    @Override
    public User selectUserById(String id) {

        return userMapper.selectByPrimaryKey(id);
    }
}
