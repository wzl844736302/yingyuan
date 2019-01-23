package com.bw.movie.bean;

public class UserInfo {
    String nickName;
    String phone;
    long birthday;
    int sex;
    long lastLoginTime;
    String headPic;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public String getHeadPic() {
        return headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

}
