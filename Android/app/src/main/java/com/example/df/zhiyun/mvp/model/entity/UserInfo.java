package com.example.df.zhiyun.mvp.model.entity;

public class UserInfo {
    private long birthday;
    private String phone;
    private String school;
    private String headImage;
    private int sex;
    private int roleId;
    private String userName;
    private String userId;
    private String email;
    private String token;
    private int schoolId;

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
    public long getBirthday() {
        return birthday;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setSchool(String school) {
        this.school = school;
    }
    public String getSchool() {
        return school;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
    public String getHeadImage() {
        return headImage;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
    public int getSex() {
        return sex;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
