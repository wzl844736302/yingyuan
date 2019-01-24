package com.bw.movie.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AllUser {
    @Id
    long id;
    String sessionId;
    int userId;
    String nickName;
    String phone;
    long birthday;
    int sex;
    long lastLoginTime;
    String headPic;

    @Generated(hash = 1348563245)
    public AllUser(long id, String sessionId, int userId, String nickName,
            String phone, long birthday, int sex, long lastLoginTime,
            String headPic) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
        this.nickName = nickName;
        this.phone = phone;
        this.birthday = birthday;
        this.sex = sex;
        this.lastLoginTime = lastLoginTime;
        this.headPic = headPic;
    }
    @Generated(hash = 1272676293)
    public AllUser() {
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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
