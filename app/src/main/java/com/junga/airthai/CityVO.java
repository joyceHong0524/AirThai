package com.junga.airthai;

public class CityVO {
    private String name;
    private int favorite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public CityVO(String name, int favorite) {
        this.name = name;
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "CityVO{" +
                "name='" + name + '\'' +
                ", favorite=" + favorite +
                '}';
    }
}
