package com.example.user.sekety;

import java.io.Serializable;

/**
 * Created by user on 4/20/2016.
 */
public class Bus implements Serializable {
    private String bus_no;
    private String st1;
    private String st2;
    private String st3;
    private String st4;
    private String st5;
    private String bus;

    public Bus(){}
    public Bus(String busno, String st_1, String st_2, String st_3, String st_4, String st_5){
        this.bus_no=busno;
        this.st1=st_1;
        this.st2=st_2;
        this.st3=st_3;
        this.st4=st_4;
        this.st5=st_5;
      }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public String getSt1() {
        return st1;
    }

    public void setSt1(String st1) {
        this.st1 = st1;
    }

    public String getSt2() {
        return st2;
    }

    public void setSt2(String st2) {
        this.st2 = st2;
    }

    public String getSt3() {
        return st3;
    }

    public void setSt3(String st3) {
        this.st3 = st3;
    }

    public String getSt4() {
        return st4;
    }

    public void setSt4(String st4) {
        this.st4 = st4;
    }

    public String getSt5() {
        return st5;
    }
    public void setSt5(String st5) {
        this.st5 = st5;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }
}
