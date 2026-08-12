package com.brewflow.inventory;

import com.brewflow.model.NguyenLieu;
import java.time.LocalDate;

public class LoNhap {
    private String maLo;
    private NguyenLieu nguyenLieu;
    private double soLuongNhap;
    private double soLuongConLai;
    private LocalDate hanSuDung;

    public LoNhap(String maLo, NguyenLieu nguyenLieu, double soLuongNhap, LocalDate hanSuDung) {
        this.maLo = maLo;
        this.nguyenLieu = nguyenLieu;
        this.soLuongNhap = soLuongNhap;
        this.soLuongConLai = soLuongNhap; 
        this.hanSuDung = hanSuDung;
    }

    public String getMaLo() {
        return maLo;
    }

    public NguyenLieu getNguyenLieu() {
        return nguyenLieu;
    }

    public double getSoLuongNhap() {
        return soLuongNhap;
    }

    public double getSoLuongConLai() {
        return soLuongConLai;
    }

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public double truBot(double soLuongMuonTru) {
        double thucTru = Math.min(soLuongConLai, soLuongMuonTru);
        soLuongConLai -= thucTru;
        return thucTru;
    }

    public boolean daHetHan() {
        return LocalDate.now().isAfter(hanSuDung);
    }

    public boolean sapHetHan(int soNgayCanhBao) {
        LocalDate nguongCanhBao = LocalDate.now().plusDays(soNgayCanhBao);
        return !daHetHan() && hanSuDung.isBefore(nguongCanhBao);
    }

    @Override
    public String toString() {
        return "Lô " + maLo + " - " + nguyenLieu.getTenNguyenLieu()
                + ": còn " + soLuongConLai + "/" + soLuongNhap + " " + nguyenLieu.getDonViDo()
                + " (HSD: " + hanSuDung + ")";
    }
}
