package cn.xkenmon.blog.vo;

import java.util.Date;

public class User {
    private int id;
    private String userName;
    private String passWd;
    private String phone;
    private String eMail;
    private Date createDate;
    private String qq;
    private boolean isAdmin;
    private int isLogin;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {

        return "----------------------------" +
                "\nid:" + id +
                "\nusername:" + userName +
                "\nE-mail:" + eMail +
                "\nPhone:" + phone +
                "\nCreateDate:" + createDate +
                "\nQQ:" + qq +
                "\nisAdmin:" + isAdmin +
                "\nisLonin:" + isLogin +
                "\navatar:" + avatar +
                "\n----------------------------";
    }
}
