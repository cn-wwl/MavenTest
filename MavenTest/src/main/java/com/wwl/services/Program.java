package com.wwl.services;

import com.wwl.dao.bean.UserInfo;
import com.wwl.dao.helper.MySqlHelper;

import java.util.Date;
import java.util.List;


/**
 * @author long
 * @date 2020年7月9日
 */
public class Program {
    public static void main(String[] args) {

          //region 用户信息管理

        //新增用户信息
        UserInfo user = new UserInfo();
        user.setName("测试");
        user.setSex("女");
        user.setAddress("浙江.杭州");
        user.setBirthday(new Date());
        user.setPhone("10000002");
        int result = MySqlHelper.InsertEntity(user, "UserInfoMapper.insertUserInfo");
        System.out.println(result > 0 ? "添加成功" : "添加失败");

        //修改用户信息
        UserInfo user2 = new UserInfo();
        user2.setId(2);
        user2.setName("管理员");
        user2.setSex("男");
        user2.setAddress("浙江.杭州");
        user2.setBirthday(new Date());
        user2.setPhone("10000001");
        int result2 = MySqlHelper.UpdateEntity(user, "UserInfoMapper.updateUserInfo");
        System.out.println(result2 > 0 ? "修改成功" : "修改失败");

        //删除用户信息
        int result3 = MySqlHelper.DeleteEntity(3, "UserInfoMapper.deleteUserInfo");
        System.out.println(result3 > 0 ? "删除成功" : "删除失败");

        //查询用户信息(单个)
        UserInfo result4 = MySqlHelper.SelectOneObject(2, "UserInfoMapper.selectUserInfo");
        System.out.println("用户对象：" + result4);

          //查询用户信息(全部)
          List<UserInfo> userInfoList = MySqlHelper.<UserInfo>SelectAllList("UserInfoMapper.selectAllUserInfo");
          System.out.println("用户列表：" + userInfoList);

          //endregion


    }
}
