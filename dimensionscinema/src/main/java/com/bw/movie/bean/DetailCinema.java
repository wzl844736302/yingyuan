package com.bw.movie.bean;

public class DetailCinema {

    /**
     * fare : 66
     * id : 3
     * imageUrl : http://172.17.8.100/images/movie/stills/xhssf/xhssf1.jpg
     * name : 西虹市首富
     * releaseTime : 1532620800000
     * summary : 故事发生在《夏洛特烦恼》中的“特烦恼”之城“西虹市”。混迹于丙级业余足球队的守门员王多鱼（沈腾 饰），因比赛失利被开除离队。正处于人生最低谷的他接受了神秘台湾财团“一个月花光十亿资金”的挑战。本以为快乐生活就此开始，王多鱼却第一次感到“花钱特烦恼”！想要人生反转走上巅峰，真的没有那么简单.
     */

    private int fare;
    private int id;
    private String imageUrl;
    private String name;
    private long releaseTime;
    private String summary;

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "DetailCinema{" +
                "fare=" + fare +
                ", id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", releaseTime=" + releaseTime +
                ", summary='" + summary + '\'' +
                '}';
    }
}
