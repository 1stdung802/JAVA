package com.brewflow.inventory;

import com.brewflow.model.NguyenLieu;

public class KhongDuNguyenLieuException extends Exception {
    private NguyenLieu nguyenLieu;
    private double soLuongCan;
    private double soLuongConLai;

    public KhongDuNguyenLieuException(NguyenLieu nguyenLieu, double soLuongCan, double soLuongConLai) {
        super("Không đủ " + nguyenLieu.getTenNguyenLieu()
                + ": cần " + soLuongCan + " " + nguyenLieu.getDonViDo()
                + ", chỉ còn " + soLuongConLai + " " + nguyenLieu.getDonViDo());
        this.nguyenLieu = nguyenLieu;
        this.soLuongCan = soLuongCan;
        this.soLuongConLai = soLuongConLai;
    }

    public NguyenLieu getNguyenLieu() {
        return nguyenLieu;
    }

    public double getSoLuongCan() {
        return soLuongCan;
    }

    public double getSoLuongConLai() {
        return soLuongConLai;
    }
}
