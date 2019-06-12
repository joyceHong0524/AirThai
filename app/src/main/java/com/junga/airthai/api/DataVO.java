package com.junga.airthai.api;

public class DataVO {
    int aqi;
    int idx;
    String dominentpol;
    Time time;
    City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getDominentpol() {
        return dominentpol;
    }

    public void setDominentpol(String dominentpol) {
        this.dominentpol = dominentpol;
    }

    public class Time {

        private String s;

        private String tz;

        private String v;

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        @Override
        public String toString() {
            return "Time{" +
                    "s='" + s + '\'' +
                    ", tz='" + tz + '\'' +
                    ", v='" + v + '\'' +
                    '}';
        }
    }

    public class City {
        private String[] geo;

        private String name;

        private String url;

        public String[] getGeo() {
            return geo;
        }

        public void setGeo(String[] geo) {
            this.geo = geo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "ClassPojo [geo = " + geo + ", name = " + name + ", url = " + url + "]";
        }
    }
}
