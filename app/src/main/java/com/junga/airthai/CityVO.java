package com.junga.airthai;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityVO cityVO = (CityVO) o;
        return favorite == cityVO.favorite &&
                Objects.equals(name, cityVO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, favorite);
    }
}
