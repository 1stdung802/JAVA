package com.brewflow.model;

import com.brewflow.strategy.PhuongPhapPhaChe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CongThucPhaChe {
    private String tenDoUong;
    private LoaiDoUong loaiDoUong;
    private List<ThanhPhanCongThuc> danhSachThanhPhan;
    private PhuongPhapPhaChe phuongPhap;

    public CongThucPhaChe(String tenDoUong, LoaiDoUong loaiDoUong,
                           List<ThanhPhanCongThuc> danhSachThanhPhan,
                           PhuongPhapPhaChe phuongPhap) {
        this.tenDoUong = tenDoUong;
        this.loaiDoUong = loaiDoUong;
        this.danhSachThanhPhan = danhSachThanhPhan;
        this.phuongPhap = phuongPhap;
    }

    public String getTenDoUong() {
        return tenDoUong;
    }

    public LoaiDoUong getLoaiDoUong() {
        return loaiDoUong;
    }

    public List<ThanhPhanCongThuc> getDanhSachThanhPhan() {
        return danhSachThanhPhan;
    }

    public Map<NguyenLieu, Double> tinhDinhLuong(Size size) {
        Map<NguyenLieu, Double> ketQua = new LinkedHashMap<>();
        for (ThanhPhanCongThuc thanhPhan : danhSachThanhPhan) {
            ketQua.put(thanhPhan.getNguyenLieu(), thanhPhan.tinhDinhLuongTheoSize(size));
        }
        return ketQua;
    }

    public String pha() {
        return phuongPhap.thucHienPha(tenDoUong);
    }

    @Override
    public String toString() {
        return tenDoUong + " (" + loaiDoUong + ")";
    }
}
