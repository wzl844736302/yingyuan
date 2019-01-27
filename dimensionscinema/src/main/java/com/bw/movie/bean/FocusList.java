package com.bw.movie.bean;


public class FocusList {

        /**
         * address : 北京市育知东路30号华联商厦4层
         * commentTotal : 0
         * distance : 0
         * followCinema : 0
         * id : 20
         * logo : http://mobile.bwstudent.com/images/movie/logo/wmyc.jpg
         * name : 北京沃美影城（回龙观店）
         */

        private String address;
        private int commentTotal;
        private int distance;
        private int followCinema;
        private int id;
        private String logo;
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCommentTotal() {
            return commentTotal;
        }

        public void setCommentTotal(int commentTotal) {
            this.commentTotal = commentTotal;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getFollowCinema() {
            return followCinema;
        }

        public void setFollowCinema(int followCinema) {
            this.followCinema = followCinema;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    @Override
    public String toString() {
        return "FocusList{" +
                "address='" + address + '\'' +
                ", commentTotal=" + commentTotal +
                ", distance=" + distance +
                ", followCinema=" + followCinema +
                ", id=" + id +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
