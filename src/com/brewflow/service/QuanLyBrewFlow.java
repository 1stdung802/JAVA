package com.brewflow.service;

import com.brewflow.inventory.KhongDuNguyenLieuException;
import com.brewflow.inventory.LoNhap;
import com.brewflow.inventory.NguyenLieuTonKho;
import com.brewflow.model.CongThucPhaChe;
import com.brewflow.model.NguyenLieu;
import com.brewflow.model.Size;
import com.brewflow.order.DonHang;
import java.util.ArrayList;
import java.util.List;


public class QuanLyBrewFlow {
    private NguyenLieuTonKho kho;
    private List<CongThucPhaChe> danhSachCongThuc;
    private List<DonHang> danhSachDonHang;
    private int soThuTuDon;

    public QuanLyBrewFlow() {
        this.kho = new NguyenLieuTonKho();
        this.danhSachCongThuc = new ArrayList<>();
        this.danhSachDonHang = new ArrayList<>();
        this.soThuTuDon = 0;
    }


    public void nhapKho(LoNhap loNhap) {
        kho.nhapKho(loNhap);
    }

    public double layTongTonKho(NguyenLieu nguyenLieu) {
        return kho.layTongTonKho(nguyenLieu);
    }

    public List<LoNhap> canhBaoSapHetHan(int soNgay) {
        return kho.canhBaoSapHetHan(soNgay);
    }

    public NguyenLieuTonKho getKho() {
        return kho;
    }

    public void themCongThuc(CongThucPhaChe congThuc) {
        danhSachCongThuc.add(congThuc);
    }

    public List<CongThucPhaChe> getDanhSachCongThuc() {
        return danhSachCongThuc;
    }

    public DonHang taoDonHang(CongThucPhaChe congThuc, Size size) {
        soThuTuDon++;
        String maDon = String.format("DH%03d", soThuTuDon);
        DonHang donMoi = new DonHang(maDon, congThuc, size);
        danhSachDonHang.add(donMoi);
        return donMoi;
    }

    public void xacNhanPha(DonHang donHang) throws KhongDuNguyenLieuException {
        donHang.xacNhanPha(kho);
    }

    public void hoanThanhDon(DonHang donHang) {
        donHang.hoanThanh();
    }

    public void huyDon(DonHang donHang) {
        donHang.huyDon();
    }

    public List<DonHang> getDanhSachDonHang() {
        return danhSachDonHang;
    }
}
