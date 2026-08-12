package com.brewflow.inventory;

import com.brewflow.model.NguyenLieu;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NguyenLieuTonKho {
    private Map<NguyenLieu, List<LoNhap>> danhSachLo = new HashMap<>();

    public void nhapKho(LoNhap loNhap) {
        danhSachLo
                .computeIfAbsent(loNhap.getNguyenLieu(), k -> new ArrayList<>())
                .add(loNhap);
    }

    public double layTongTonKho(NguyenLieu nguyenLieu) {
        List<LoNhap> cacLo = danhSachLo.getOrDefault(nguyenLieu, new ArrayList<>());
        double tong = 0;
        for (LoNhap lo : cacLo) {
            tong += lo.getSoLuongConLai();
        }
        return tong;
    }

    public boolean kiemTraDuNguyenLieu(NguyenLieu nguyenLieu, double soLuongCan) {
        return layTongTonKho(nguyenLieu) >= soLuongCan;
    }

    public void truKho(NguyenLieu nguyenLieu, double soLuongCan) throws KhongDuNguyenLieuException {
        double tongConLai = layTongTonKho(nguyenLieu);
        if (tongConLai < soLuongCan) {
            throw new KhongDuNguyenLieuException(nguyenLieu, soLuongCan, tongConLai);
        }

        List<LoNhap> cacLo = danhSachLo.get(nguyenLieu);
        cacLo.sort(Comparator.comparing(LoNhap::getHanSuDung)); // FIFO theo HSD

        double conLaiCanTru = soLuongCan;
        for (LoNhap lo : cacLo) {
            if (conLaiCanTru <= 0) break;
            double daTru = lo.truBot(conLaiCanTru);
            conLaiCanTru -= daTru;
        }
    }

    public List<LoNhap> canhBaoSapHetHan(int soNgayCanhBao) {
        List<LoNhap> ketQua = new ArrayList<>();
        for (List<LoNhap> cacLo : danhSachLo.values()) {
            for (LoNhap lo : cacLo) {
                if (lo.sapHetHan(soNgayCanhBao) && lo.getSoLuongConLai() > 0) {
                    ketQua.add(lo);
                }
            }
        }
        return ketQua;
    }

    public List<LoNhap> layDanhSachLo(NguyenLieu nguyenLieu) {
        return danhSachLo.getOrDefault(nguyenLieu, new ArrayList<>());
    }
}
