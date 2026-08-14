package com.brewflow.model;

/**
 * Kế thừa CongThucPhaChe: thêm thuộc tính riêng (loaiTraiCay) và
 * override tinhGia() - trà trái cây luôn cộng thêm phụ phí trái cây
 * tươi cố định.
 */
public class TraTraiCay extends CongThucPhaChe {
    private String loaiTraiCay;

    public TraTraiCay(String maCongThuc, String tenDoUong, double giaCoBan, String trangThai, String loaiTraiCay) {
        super(maCongThuc, tenDoUong, giaCoBan, trangThai);
        this.loaiTraiCay = loaiTraiCay;
    }

    public String getLoaiTraiCay() {
        return loaiTraiCay;
    }

    public void setLoaiTraiCay(String loaiTraiCay) {
        this.loaiTraiCay = loaiTraiCay;
    }

    @Override
    public double tinhGia() {
        return getGiaCoBan() + 3000;
    }
}
