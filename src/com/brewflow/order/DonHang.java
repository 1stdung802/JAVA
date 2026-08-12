package com.brewflow.order;

import com.brewflow.inventory.KhongDuNguyenLieuException;
import com.brewflow.inventory.NguyenLieuTonKho;
import com.brewflow.model.CongThucPhaChe;
import com.brewflow.model.Size;

public class DonHang {
    private String maDonHang;
    private CongThucPhaChe congThucPhaChe;
    private Size size;
    private TrangThaiDonHang trangThaiHienTai;

    public DonHang(String maDonHang, CongThucPhaChe congThucPhaChe, Size size) {
        this.maDonHang = maDonHang;
        this.congThucPhaChe = congThucPhaChe;
        this.size = size;
        this.trangThaiHienTai = new ChoPhaChe(); // mọi đơn mới đều bắt đầu ở trạng thái này
    }

    public void xacNhanPha(NguyenLieuTonKho kho) throws KhongDuNguyenLieuException {
        trangThaiHienTai.xacNhanPha(this, kho);
    }

    public void hoanThanh() {
        trangThaiHienTai.hoanThanh(this);
    }

    public void huyDon() {
        trangThaiHienTai.huyDon(this);
    }

    // Chỉ các class trong package order (các TrangThaiDonHang) mới được
    // đổi trạng thái - DonHang không tự đổi trạng thái của chính mình.
    void setTrangThai(TrangThaiDonHang trangThaiMoi) {
        this.trangThaiHienTai = trangThaiMoi;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public CongThucPhaChe getCongThucPhaChe() {
        return congThucPhaChe;
    }

    public Size getSize() {
        return size;
    }

    public String getTenTrangThai() {
        return trangThaiHienTai.tenTrangThai();
    }

    @Override
    public String toString() {
        return "Đơn " + maDonHang + " - " + congThucPhaChe.getTenDoUong()
                + " (size " + size + ") - Trạng thái: " + getTenTrangThai();
    }
}