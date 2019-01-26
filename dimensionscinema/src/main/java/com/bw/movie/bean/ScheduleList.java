package com.bw.movie.bean;

public class ScheduleList {

    /**
     * beginTime : 19:20
     * duration : 118分钟
     * endTime : 21:18
     * id : 1
     * screeningHall : 2号厅
     * seatsTotal : 150
     * seatsUseCount : 50
     * status : 1
     */

    private String beginTime;
    private String duration;
    private String endTime;
    private int id;
    private String screeningHall;
    private int seatsTotal;
    private int seatsUseCount;
    private int status;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScreeningHall() {
        return screeningHall;
    }

    public void setScreeningHall(String screeningHall) {
        this.screeningHall = screeningHall;
    }

    public int getSeatsTotal() {
        return seatsTotal;
    }

    public void setSeatsTotal(int seatsTotal) {
        this.seatsTotal = seatsTotal;
    }

    public int getSeatsUseCount() {
        return seatsUseCount;
    }

    public void setSeatsUseCount(int seatsUseCount) {
        this.seatsUseCount = seatsUseCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ScheduleList{" +
                "beginTime='" + beginTime + '\'' +
                ", duration='" + duration + '\'' +
                ", endTime='" + endTime + '\'' +
                ", id=" + id +
                ", screeningHall='" + screeningHall + '\'' +
                ", seatsTotal=" + seatsTotal +
                ", seatsUseCount=" + seatsUseCount +
                ", status=" + status +
                ", price=" + price +
                '}';
    }
}
