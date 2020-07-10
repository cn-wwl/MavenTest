package com.wwl.services;

import com.alibaba.fastjson.JSON;
import com.wwl.dao.bean.UserInfo;
import com.wwl.dao.helper.MySqlHelper;
import com.wwl.util.HttpServletRequestBodyUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * @author long
 * date 2020年7月7日
 */

@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            List<UserInfo> userInfoList = MySqlHelper.<UserInfo>SelectAllList("UserInfoMapper.selectAllUserInfo");

            String jsonStr = JSON.toJSONString(userInfoList);
            response.getWriter().println(jsonStr);

        } else {
            UserInfo userInfo = MySqlHelper.<UserInfo>SelectOneObject(Integer.parseInt(id), "UserInfoMapper.selectUserInfo");

            String jsonStr = JSON.toJSONString(userInfo);
            response.getWriter().println(jsonStr);
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        StringBuilder erromessage = new StringBuilder();
        String data = HttpServletRequestBodyUtil.readAsChars(request);
        UserInfo user = JSON.parseObject(data, UserInfo.class);

        if (user.getName() == null || user.getName().isEmpty()) {
            erromessage.append("姓名不能为空!");
        }
        if (user.getSex() == null || user.getSex().isEmpty()) {
            erromessage.append("性别不能为空!");
        }

        if (erromessage != null) {
            response.getWriter().println(erromessage);
        }

        int result = MySqlHelper.InsertEntity(user, "UserInfoMapper.insertUserInfo");
        response.getWriter().println(result > 0 ? "添加成功" : "添加失败");
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        StringBuilder erromessage = new StringBuilder();
        String data = HttpServletRequestBodyUtil.readAsChars(request);
        UserInfo user = JSON.parseObject(data, UserInfo.class);

        if (user.getName() == null || user.getName().isEmpty()) {
            erromessage.append("姓名不能为空!");
        }
        if (user.getSex() == null || user.getSex().isEmpty()) {
            erromessage.append("性别不能为空!");
        }
        if (erromessage != null) {
            response.getWriter().println(erromessage);
        }

        int result = MySqlHelper.UpdateEntity(user, "UserInfoMapper.updateUserInfo");
        response.getWriter().println(result > 0 ? "更新成功" : "更新失败");
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {

            response.getWriter().println("id不能为空!");
        }

        int result = MySqlHelper.DeleteEntity(Integer.parseInt(id), "UserInfoMapper.deleteUserInfo");
        response.getWriter().println(result > 0 ? "删除成功" : "删除失败");

    }

}
