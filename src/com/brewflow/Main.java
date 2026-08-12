package com.brewflow;

import com.brewflow.inventory.*;
import com.brewflow.model.*;
import com.brewflow.order.*;
import com.brewflow.strategy.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Tạo nguyên liệu
        NguyenLieu traiCay = new NguyenLieu("Trà đen", "ml", 5000);
        NguyenLieu suaTuoi = new NguyenLieu("Sữa tươi", "ml", 8000);
        NguyenLieu tranChau = new NguyenLieu("Trân châu", "g", 3000);

        // Tạo công thức: Trà sữa trân châu, pha bằng cách khuấy
        CongThucPhaChe traSua = new CongThucPhaChe(
                "Trà sữa trân châu",
                LoaiDoUong.TRA_SUA,
                Arrays.asList(
                        new ThanhPhanCongThuc(traiCay, 150),
                        new ThanhPhanCongThuc(suaTuoi, 50),
                        new ThanhPhanCongThuc(tranChau, 30)
                ),
                new Khuay()
        );

        // In công thức chuẩn (size M)
        System.out.println("=== Công thức: " + traSua + " ===");
        for (ThanhPhanCongThuc tp : traSua.getDanhSachThanhPhan()) {
            System.out.println("  " + tp);
        }

        // Tính định lượng cho size L
        System.out.println("\n=== Định lượng cho size L ===");
        Map<NguyenLieu, Double> dinhLuongL = traSua.tinhDinhLuong(Size.L);
        for (Map.Entry<NguyenLieu, Double> entry : dinhLuongL.entrySet()) {
            System.out.println("  " + entry.getKey().getTenNguyenLieu()
                    + ": " + entry.getValue() + " " + entry.getKey().getDonViDo());
        }

        // Thực hiện pha chế (đa hình - gọi qua interface PhuongPhapPhaChe)
        System.out.println("\n=== Thực hiện pha chế ===");
        System.out.println(traSua.pha());

        // ===== GIAI ĐOẠN 2: KHO NGUYÊN LIỆU (FIFO theo HSD) =====
        NguyenLieuTonKho kho = new NguyenLieuTonKho();

        // Nhập 2 lô trà đen, lô A hết hạn trước lô B
        kho.nhapKho(new LoNhap("TD-A", traiCay, 500, LocalDate.now().plusDays(3)));
        kho.nhapKho(new LoNhap("TD-B", traiCay, 1000, LocalDate.now().plusDays(20)));
        kho.nhapKho(new LoNhap("ST-A", suaTuoi, 300, LocalDate.now().plusDays(10)));
        kho.nhapKho(new LoNhap("TC-A", tranChau, 200, LocalDate.now().plusDays(2)));

        System.out.println("\n=== Tồn kho ban đầu ===");
        System.out.println("Trà đen: " + kho.layTongTonKho(traiCay) + " ml");
        System.out.println("Sữa tươi: " + kho.layTongTonKho(suaTuoi) + " ml");
        System.out.println("Trân châu: " + kho.layTongTonKho(tranChau) + " g");

        // Trừ kho theo định lượng size L vừa tính ở trên
        System.out.println("\n=== Trừ kho cho 1 đơn size L ===");
        try {
            for (Map.Entry<NguyenLieu, Double> entry : dinhLuongL.entrySet()) {
                kho.truKho(entry.getKey(), entry.getValue());
            }
            System.out.println("Trừ kho thành công!");
        } catch (KhongDuNguyenLieuException e) {
            System.out.println("Lỗi trừ kho: " + e.getMessage());
        }

        System.out.println("\n=== Chi tiết lô trà đen sau khi trừ (kiểm tra FIFO) ===");
        for (LoNhap lo : kho.layDanhSachLo(traiCay)) {
            System.out.println("  " + lo);
        }
        // Kỳ vọng: lô TD-A (hết hạn sớm hơn) bị trừ trước, TD-B gần như còn nguyên

        // Thử trừ kho vượt quá tồn kho để kiểm tra exception
        System.out.println("\n=== Thử trừ vượt quá tồn kho trân châu ===");
        try {
            kho.truKho(tranChau, 9999);
            System.out.println("Trừ kho thành công!");
        } catch (KhongDuNguyenLieuException e) {
            System.out.println("Lỗi trừ kho (đúng như kỳ vọng): " + e.getMessage());
        }

        // Cảnh báo nguyên liệu sắp hết hạn trong 5 ngày tới
        System.out.println("\n=== Cảnh báo nguyên liệu sắp hết hạn (trong 5 ngày) ===");
        for (LoNhap lo : kho.canhBaoSapHetHan(5)) {
            System.out.println("  " + lo);
        }

        // ===== GIAI ĐOẠN 3: ĐƠN HÀNG & STATE PATTERN =====
        System.out.println("\n\n===== VÒNG ĐỜI ĐƠN HÀNG (State Pattern) =====");

        DonHang don1 = new DonHang("DH001", traSua, Size.M);
        System.out.println("\n1. Tạo đơn: " + don1);

        // Luồng hợp lệ: Chờ pha chế -> Đang pha chế -> Hoàn thành
        try {
            don1.xacNhanPha(kho);
            System.out.println("2. Xác nhận pha chế OK: " + don1);

            don1.hoanThanh();
            System.out.println("3. Hoàn thành đơn OK: " + don1);
        } catch (KhongDuNguyenLieuException e) {
            System.out.println("Lỗi thiếu nguyên liệu: " + e.getMessage());
        }

        // Thử hủy đơn đã hoàn thành -> phải bị chặn bởi State Pattern
        System.out.println("\n4. Thử hủy đơn đã hoàn thành (kỳ vọng bị chặn):");
        try {
            don1.huyDon();
            System.out.println("   Hủy thành công (KHÔNG đúng kỳ vọng!)");
        } catch (IllegalStateException e) {
            System.out.println("   Bị chặn đúng như kỳ vọng: " + e.getMessage());
        }

        // Đơn thứ 2: tạo và hủy ngay khi đang chờ pha chế
        DonHang don2 = new DonHang("DH002", traSua, Size.S);
        System.out.println("\n5. Tạo đơn thứ 2: " + don2);
        don2.huyDon();
        System.out.println("6. Hủy đơn khi đang chờ pha chế OK: " + don2);

        // Thử xác nhận pha chế cho đơn đã hủy -> phải bị chặn
        System.out.println("\n7. Thử xác nhận pha chế cho đơn đã hủy (kỳ vọng bị chặn):");
        try {
            don2.xacNhanPha(kho);
            System.out.println("   Xác nhận thành công (KHÔNG đúng kỳ vọng!)");
        } catch (IllegalStateException e) {
            System.out.println("   Bị chặn đúng như kỳ vọng: " + e.getMessage());
        } catch (KhongDuNguyenLieuException e) {
            System.out.println("   Lỗi thiếu nguyên liệu: " + e.getMessage());
        }
    }
}
