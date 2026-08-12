package com.brewflow.order;

public class DangPhaChe implements TrangThaiDonHang {

    @Override
    public void hoanThanh(DonHang donHang) {
        donHang.setTrangThai(new HoanThanh());
    }

    @Override
    public void huyDon(DonHang donHang) {
        donHang.setTrangThai(new DaHuy());
    }

    @Override
    public String tenTrangThai() {
        return "Đang pha chế";
    }
}
