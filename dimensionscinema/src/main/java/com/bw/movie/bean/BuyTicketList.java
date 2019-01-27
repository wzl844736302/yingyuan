package com.bw.movie.bean;

import java.util.List;

public class BuyTicketList {

        /**
         * amount : 1
         * beginTime : 17:00:00
         * cinemaName : CGV影城（清河店）
         * createTime : 1548588289000
         * endTime : 04:56:00
         * id : 5997
         * movieName : 无双
         * orderId : 20190127192449406
         * price : 0.15
         * screeningHall : 4号厅
         * status : 1
         * userId : 1782
         */

        private int amount;
        private String beginTime;
        private String cinemaName;
        private long createTime;
        private String endTime;
        private int id;
        private String movieName;
        private String orderId;
        private double price;
        private String screeningHall;
        private int status;
        private int userId;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
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

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getScreeningHall() {
            return screeningHall;
        }

        public void setScreeningHall(String screeningHall) {
            this.screeningHall = screeningHall;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

    @Override
    public String toString() {
        return "BuyTicketList{" +
                "amount=" + amount +
                ", beginTime='" + beginTime + '\'' +
                ", cinemaName='" + cinemaName + '\'' +
                ", createTime=" + createTime +
                ", endTime='" + endTime + '\'' +
                ", id=" + id +
                ", movieName='" + movieName + '\'' +
                ", orderId='" + orderId + '\'' +
                ", price=" + price +
                ", screeningHall='" + screeningHall + '\'' +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }
}
