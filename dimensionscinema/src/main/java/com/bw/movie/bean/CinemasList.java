package com.bw.movie.bean;

public class CinemasList {

    /**
     * address : 北京市崇文区崇文门外大街18号国瑞城首层、地下二层
     * followCinema : false
     * id : 9
     * name : 北京百老汇影城国瑞购物中心店
     */

    private String address;
    private int followCinema;
    private int id;
    private String name;
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int isFollowCinema() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CinemasList{" +
                "address='" + address + '\'' +
                ", followCinema=" + followCinema +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
