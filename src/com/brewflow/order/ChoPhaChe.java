package com.brewflow.order;

import com.brewflow.inventory.KhongDuNguyenLieuException;
import com.brewflow.inventory.NguyenLieuTonKho;
import com.brewflow.model.NguyenLieu;
import java.util.Map;

public class ChoPhaChe implements TrangThaiDonHang {

    @Override
    public void xacNhanPha(DonHang donHang, NguyenLieuTonKho kho) throws KhongDuNguyenLieuException {
        Map<NguyenLieu, Double> yeuCau = donHang.getCongThucPhaChe().tinhDinhLuong(donHang.getSize());

        for (Map.Entry<NguyenLieu, Double> entry : yeuCau.entrySet()) {
            if (!kho.kiemTraDuNguyenLieu(entry.getKey(), entry.getValue())) {
                throw new KhongDuNguyenLieuException(
                        entry.getKey(), entry.getValue(), kho.layTongTonKho(entry.getKey()));
            }
        }

        for (Map.Entry<NguyenLieu, Double> entry : yeuCau.entrySet()) {
            kho.truKho(entry.getKey(), entry.getValue());
        }

        donHang.setTrangThai(new DangPhaChe());
    }

    @Override
    public void huyDon(DonHang donHang) {
        donHang.setTrangThai(new DaHuy());
    }

    @Override
    public String tenTrangThai() {
        return "Chờ pha chế";
    }
}
