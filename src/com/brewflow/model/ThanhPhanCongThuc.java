package com.brewflow.model;

public class ThanhPhanCongThuc {
    private NguyenLieu nguyenLieu;
    private double dinhLuongChuan;

    public ThanhPhanCongThuc(NguyenLieu nguyenLieu, double dinhLuongChuan) {
        this.nguyenLieu = nguyenLieu;
        this.dinhLuongChuan = dinhLuongChuan;
    }

    public NguyenLieu getNguyenLieu() {
        return nguyenLieu;
    }

    public double getDinhLuongChuan() {
        return dinhLuongChuan;
    }

    
    public double tinhDinhLuongTheoSize(Size size) {
        return dinhLuongChuan * size.getHeSoNhan();
    }

    @Override
    public String toString() {
        return nguyenLieu.getTenNguyenLieu() + ": " + dinhLuongChuan + " " + nguyenLieu.getDonViDo() + " (chuẩn size M)";
    }
}
