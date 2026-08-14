package com.brewflow.model;

import java.io.Serializable;

public class CongThucPhaChe implements Serializable, IHienThi {
    private String maCongThuc;
    private String tenDoUong;
    private double giaCoBan;
    private String trangThai;

    public CongThucPhaChe(String maCongThuc, String tenDoUong, double giaCoBan, String trangThai) {
        this.maCongThuc = maCongThuc;
        this.tenDoUong = tenDoUong;
        this.giaCoBan = giaCoBan;
        this.trangThai = trangThai;
    }

    public String getMaCongThuc() {
        return maCongThuc;
    }

    public void setMaCongThuc(String maCongThuc) {
        this.maCongThuc = maCongThuc;
    }

    public String getTenDoUong() {
        return tenDoUong;
    }

    public void setTenDoUong(String tenDoUong) {
        this.tenDoUong = tenDoUong;
    }

    public double getGiaCoBan() {
        return giaCoBan;
    }

    public void setGiaCoBan(double giaCoBan) {
        this.giaCoBan = giaCoBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public double tinhGia() {
        return giaCoBan;
    }

    @Override
    public String hienThiThongTin() {
        return String.format("[%s] %s - %,.0f đ - %s (%s)",
                maCongThuc, tenDoUong, tinhGia(), trangThai, getClass().getSimpleName());
    }
}
