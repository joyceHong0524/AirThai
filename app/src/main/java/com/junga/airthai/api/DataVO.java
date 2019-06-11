package com.junga.airthai.api;

public class DataVO {
    int aqi;
    int idx;
    String dominentpol;
    Time time;

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
}
