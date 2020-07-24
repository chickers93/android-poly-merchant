package model;

import java.io.Serializable;

public class Menu_temp implements Serializable {
    private String mamonan;
    private String tenmonan;
    private int gia;
    private String hinhanh;
    private int soluong;


    public Menu_temp(String mamonan, String tenmonan, int gia, String hinhanh, int soluong) {
        this.mamonan = mamonan;
        this.tenmonan = tenmonan;
        this.gia = gia;
        this.hinhanh = hinhanh;
        this.soluong = soluong;
    }

    public Menu_temp() {
    }

    public String getMamonan() {
        return mamonan;
    }

    public void setMamonan(String mamonan) {
        this.mamonan = mamonan;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
