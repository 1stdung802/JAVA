package com.brewflow.model;

public class NguyenLieu {
    private String tenNguyenLieu;
    private String donViDo;
    private double giaTien;

    public NguyenLieu(String tenNguyenLieu, String donViDo, double giaTien) {
        this.tenNguyenLieu = tenNguyenLieu;
        this.donViDo = donViDo;
        this.giaTien = giaTien;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public String getDonViDo() {
        return donViDo;
    }

    public double getGiaTien() {
        return giaTien;
    }

    @Override
    public String toString() {
        return tenNguyenLieu + " (" + donViDo + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NguyenLieu)) return false;
        NguyenLieu other = (NguyenLieu) o;
        return tenNguyenLieu.equalsIgnoreCase(other.tenNguyenLieu);
    }

    @Override
    public int hashCode() {
        return tenNguyenLieu.toLowerCase().hashCode();
    }
}
