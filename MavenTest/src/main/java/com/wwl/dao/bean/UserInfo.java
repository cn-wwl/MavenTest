package com.wwl.dao.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author long
 * @date 2020年7月7日
 */
public class UserInfo implements Serializable {
    private int id;
    private String name;
    @JSONField(ordinal = 1)
    private String sex;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    private String address;
    private String phone;

    //region getter、setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //endregion

//    @Override
//    public String toString()
//    {
//        return "User [id=" + id + ", username=" + name + ", sex=" + sex
//                + ", birthday=" + DateFormat.getDateTimeInstance().format(birthday) + ", address=" + address + ", phone=" + phone + "]";
//    }
}
