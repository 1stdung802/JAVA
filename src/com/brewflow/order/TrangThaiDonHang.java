package com.brewflow.order;

import com.brewflow.inventory.KhongDuNguyenLieuException;
import com.brewflow.inventory.NguyenLieuTonKho;

public interface TrangThaiDonHang {

    default void xacNhanPha(DonHang donHang, NguyenLieuTonKho kho) throws KhongDuNguyenLieuException {
        throw new IllegalStateException(
                "Không thể xác nhận pha chế từ trạng thái \"" + tenTrangThai() + "\"");
    }

    default void hoanThanh(DonHang donHang) {
        throw new IllegalStateException(
                "Không thể hoàn thành đơn từ trạng thái \"" + tenTrangThai() + "\"");
    }

    default void huyDon(DonHang donHang) {
        throw new IllegalStateException(
                "Không thể hủy đơn từ trạng thái \"" + tenTrangThai() + "\"");
    }

    String tenTrangThai();
}
