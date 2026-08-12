package com.brewflow.service;

import com.brewflow.inventory.LoNhap;
import com.brewflow.model.*;
import com.brewflow.strategy.*;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Tách riêng việc khởi tạo dữ liệu mẫu (menu + tồn kho ban đầu) ra khỏi
 * BrewFlowGUI - lớp GUI chỉ nên lo việc dựng layout, không nên gánh
 * thêm hàng trăm dòng khai báo nguyên liệu/công thức. Gọi napDuLieuMau()
 * một lần duy nhất khi khởi động ứng dụng.
 *
 * Menu gồm 25 công thức, phủ đủ 4 loại đồ uống và cả 4 phương pháp
 * pha chế, để thể hiện rõ tính đa hình (polymorphism) của Strategy
 * Pattern ở package strategy.
 */
public class DuLieuMau {

    public static void napDuLieuMau(QuanLyBrewFlow quanLy) {
        // ===== NGUYÊN LIỆU =====
        NguyenLieu traDen = new NguyenLieu("Trà đen", "ml", 5000);
        NguyenLieu traOLong = new NguyenLieu("Trà ô long", "ml", 6000);
        NguyenLieu traLai = new NguyenLieu("Trà lài (hoa nhài)", "ml", 6000);
        NguyenLieu suaTuoi = new NguyenLieu("Sữa tươi", "ml", 8000);
        NguyenLieu suaDac = new NguyenLieu("Sữa đặc", "ml", 6000);
        NguyenLieu duongNuoc = new NguyenLieu("Đường nước", "ml", 3000);
        NguyenLieu daXay = new NguyenLieu("Đá xay", "g", 500);
        NguyenLieu espresso = new NguyenLieu("Espresso shot", "shot", 4000);
        NguyenLieu nuocNong = new NguyenLieu("Nước nóng", "ml", 0);
        NguyenLieu caPhePhin = new NguyenLieu("Cà phê phin (rang xay)", "g", 15000);
        NguyenLieu coldBrewCoffee = new NguyenLieu("Cold brew cà phê", "ml", 12000);
        NguyenLieu cotDua = new NguyenLieu("Cốt dừa", "ml", 9000);
        NguyenLieu caramelSyrup = new NguyenLieu("Sốt caramel", "ml", 10000);
        NguyenLieu nuocCotChanhVang = new NguyenLieu("Nước cốt chanh vàng", "ml", 8000);
        NguyenLieu muiBuoi = new NguyenLieu("Múi bưởi", "g", 15000);
        NguyenLieu siroMo = new NguyenLieu("Siro mơ", "ml", 12000);
        NguyenLieu nuocCamTuoi = new NguyenLieu("Nước cam tươi", "ml", 9000);
        NguyenLieu daoMieng = new NguyenLieu("Đào miếng", "g", 12000);
        NguyenLieu saTuoi = new NguyenLieu("Sả tươi", "g", 2000);
        NguyenLieu mangCau = new NguyenLieu("Mãng cầu", "g", 14000);
        NguyenLieu oiHong = new NguyenLieu("Ổi hồng", "g", 11000);
        NguyenLieu duaMieng = new NguyenLieu("Dứa (thơm)", "g", 9000);
        NguyenLieu chanhLeo = new NguyenLieu("Chanh leo", "ml", 13000);
        NguyenLieu vai = new NguyenLieu("Vải", "g", 16000);
        NguyenLieu xoai = new NguyenLieu("Xoài", "g", 10000);
        NguyenLieu dauTay = new NguyenLieu("Dâu tây", "g", 18000);
        NguyenLieu bo = new NguyenLieu("Bơ", "g", 12000);
        NguyenLieu suaChua = new NguyenLieu("Sữa chua", "ml", 7000);

        // ===== CÔNG THỨC: TRÀ SỮA (Lắc - LacShake) =====
        CongThucPhaChe hongTraSua = new CongThucPhaChe("Hồng trà sữa", LoaiDoUong.TRA_SUA,
                Arrays.asList(
                        new ThanhPhanCongThuc(traDen, 120),
                        new ThanhPhanCongThuc(suaTuoi, 60),
                        new ThanhPhanCongThuc(duongNuoc, 20)
                ), new LacShake());

        CongThucPhaChe oLongSua = new CongThucPhaChe("Ô long sữa", LoaiDoUong.TRA_SUA,
                Arrays.asList(
                        new ThanhPhanCongThuc(traOLong, 120),
                        new ThanhPhanCongThuc(suaTuoi, 60),
                        new ThanhPhanCongThuc(duongNuoc, 20)
                ), new LacShake());

        CongThucPhaChe nhaiLaiSua = new CongThucPhaChe("Nhài lài sữa", LoaiDoUong.TRA_SUA,
                Arrays.asList(
                        new ThanhPhanCongThuc(traLai, 120),
                        new ThanhPhanCongThuc(suaTuoi, 60),
                        new ThanhPhanCongThuc(duongNuoc, 20)
                ), new LacShake());

        // ===== CÔNG THỨC: CÀ PHÊ (Khuấy - Khuay) =====
        CongThucPhaChe caPheDen = new CongThucPhaChe("Cà phê đen", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(caPhePhin, 20),
                        new ThanhPhanCongThuc(nuocNong, 100)
                ), new Khuay());

        CongThucPhaChe caPheNau = new CongThucPhaChe("Cà phê nâu", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(caPhePhin, 20),
                        new ThanhPhanCongThuc(nuocNong, 80),
                        new ThanhPhanCongThuc(suaDac, 30)
                ), new Khuay());

        CongThucPhaChe bacXiu = new CongThucPhaChe("Bạc xỉu", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(caPhePhin, 15),
                        new ThanhPhanCongThuc(nuocNong, 60),
                        new ThanhPhanCongThuc(suaDac, 40),
                        new ThanhPhanCongThuc(suaTuoi, 40)
                ), new Khuay());

        // ===== CÔNG THỨC: CÀ PHÊ (Pha máy - PhaMay) =====
        CongThucPhaChe espressoDon = new CongThucPhaChe("Espresso", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(espresso, 2)
                ), new PhaMay());

        CongThucPhaChe americano = new CongThucPhaChe("Americano", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(espresso, 2),
                        new ThanhPhanCongThuc(nuocNong, 150)
                ), new PhaMay());

        CongThucPhaChe capuchino = new CongThucPhaChe("Capuchino", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(espresso, 2),
                        new ThanhPhanCongThuc(suaTuoi, 100)
                ), new PhaMay());

        CongThucPhaChe latte = new CongThucPhaChe("Latte", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(espresso, 2),
                        new ThanhPhanCongThuc(suaTuoi, 150)
                ), new PhaMay());

        // ===== CÔNG THỨC: CÀ PHÊ - COLDBREW (Lắc - LacShake) =====
        CongThucPhaChe coldbrewChanhVang = new CongThucPhaChe("Coldbrew chanh vàng", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(coldBrewCoffee, 150),
                        new ThanhPhanCongThuc(nuocCotChanhVang, 20),
                        new ThanhPhanCongThuc(duongNuoc, 15)
                ), new LacShake());

        CongThucPhaChe coldbrewBuoi = new CongThucPhaChe("Coldbrew bưởi", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(coldBrewCoffee, 150),
                        new ThanhPhanCongThuc(muiBuoi, 30),
                        new ThanhPhanCongThuc(duongNuoc, 15)
                ), new LacShake());

        CongThucPhaChe coldbrewMo = new CongThucPhaChe("Coldbrew mơ", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(coldBrewCoffee, 150),
                        new ThanhPhanCongThuc(siroMo, 20),
                        new ThanhPhanCongThuc(duongNuoc, 10)
                ), new LacShake());

        CongThucPhaChe coldbrewCam = new CongThucPhaChe("Coldbrew cam", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(coldBrewCoffee, 150),
                        new ThanhPhanCongThuc(nuocCamTuoi, 30),
                        new ThanhPhanCongThuc(duongNuoc, 10)
                ), new LacShake());

        // ===== CÔNG THỨC: CÀ PHÊ (Xay - Xay) =====
        CongThucPhaChe caPheCotDua = new CongThucPhaChe("Cà phê cốt dừa", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(espresso, 2),
                        new ThanhPhanCongThuc(cotDua, 60),
                        new ThanhPhanCongThuc(daXay, 100)
                ), new Xay());

        CongThucPhaChe caramelMachiatoDaXay = new CongThucPhaChe("Caramel machiato đá xay", LoaiDoUong.CA_PHE,
                Arrays.asList(
                        new ThanhPhanCongThuc(espresso, 2),
                        new ThanhPhanCongThuc(suaTuoi, 100),
                        new ThanhPhanCongThuc(caramelSyrup, 20),
                        new ThanhPhanCongThuc(daXay, 100)
                ), new Xay());

        // ===== CÔNG THỨC: TRÀ TRÁI CÂY (Lắc - LacShake) =====
        CongThucPhaChe traDaoCamSa = new CongThucPhaChe("Trà đào cam sả", LoaiDoUong.TRA_TRAI_CAY,
                Arrays.asList(
                        new ThanhPhanCongThuc(traDen, 100),
                        new ThanhPhanCongThuc(daoMieng, 50),
                        new ThanhPhanCongThuc(nuocCamTuoi, 30),
                        new ThanhPhanCongThuc(saTuoi, 5)
                ), new LacShake());

        CongThucPhaChe traMangCau = new CongThucPhaChe("Trà mãng cầu", LoaiDoUong.TRA_TRAI_CAY,
                Arrays.asList(
                        new ThanhPhanCongThuc(traDen, 100),
                        new ThanhPhanCongThuc(mangCau, 60),
                        new ThanhPhanCongThuc(duongNuoc, 20)
                ), new LacShake());

        CongThucPhaChe traOiHong = new CongThucPhaChe("Trà ổi hồng", LoaiDoUong.TRA_TRAI_CAY,
                Arrays.asList(
                        new ThanhPhanCongThuc(traDen, 100),
                        new ThanhPhanCongThuc(oiHong, 60),
                        new ThanhPhanCongThuc(duongNuoc, 20)
                ), new LacShake());

        CongThucPhaChe traDuaChanhLeo = new CongThucPhaChe("Trà dứa chanh leo", LoaiDoUong.TRA_TRAI_CAY,
                Arrays.asList(
                        new ThanhPhanCongThuc(traDen, 100),
                        new ThanhPhanCongThuc(duaMieng, 50),
                        new ThanhPhanCongThuc(chanhLeo, 30)
                ), new LacShake());

        CongThucPhaChe traVai = new CongThucPhaChe("Trà vải", LoaiDoUong.TRA_TRAI_CAY,
                Arrays.asList(
                        new ThanhPhanCongThuc(traDen, 100),
                        new ThanhPhanCongThuc(vai, 60),
                        new ThanhPhanCongThuc(duongNuoc, 15)
                ), new LacShake());

        // ===== CÔNG THỨC: SINH TỐ (Xay - Xay) =====
        CongThucPhaChe sinhToXoai = new CongThucPhaChe("Sinh tố xoài", LoaiDoUong.SINH_TO,
                Arrays.asList(
                        new ThanhPhanCongThuc(xoai, 150),
                        new ThanhPhanCongThuc(suaChua, 50),
                        new ThanhPhanCongThuc(suaTuoi, 30)
                ), new Xay());

        CongThucPhaChe sinhToDau = new CongThucPhaChe("Sinh tố dâu", LoaiDoUong.SINH_TO,
                Arrays.asList(
                        new ThanhPhanCongThuc(dauTay, 150),
                        new ThanhPhanCongThuc(suaChua, 50),
                        new ThanhPhanCongThuc(suaTuoi, 30)
                ), new Xay());

        CongThucPhaChe sinhToBo = new CongThucPhaChe("Sinh tố bơ", LoaiDoUong.SINH_TO,
                Arrays.asList(
                        new ThanhPhanCongThuc(bo, 150),
                        new ThanhPhanCongThuc(suaDac, 40),
                        new ThanhPhanCongThuc(suaTuoi, 50)
                ), new Xay());

        CongThucPhaChe sinhToMangCau = new CongThucPhaChe("Sinh tố mãng cầu", LoaiDoUong.SINH_TO,
                Arrays.asList(
                        new ThanhPhanCongThuc(mangCau, 150),
                        new ThanhPhanCongThuc(suaChua, 50),
                        new ThanhPhanCongThuc(suaTuoi, 30)
                ), new Xay());

        // ===== Đăng ký toàn bộ 25 công thức =====
        quanLy.themCongThuc(hongTraSua);
        quanLy.themCongThuc(oLongSua);
        quanLy.themCongThuc(nhaiLaiSua);
        quanLy.themCongThuc(caPheDen);
        quanLy.themCongThuc(caPheNau);
        quanLy.themCongThuc(bacXiu);
        quanLy.themCongThuc(espressoDon);
        quanLy.themCongThuc(americano);
        quanLy.themCongThuc(capuchino);
        quanLy.themCongThuc(latte);
        quanLy.themCongThuc(coldbrewChanhVang);
        quanLy.themCongThuc(coldbrewBuoi);
        quanLy.themCongThuc(coldbrewMo);
        quanLy.themCongThuc(coldbrewCam);
        quanLy.themCongThuc(caPheCotDua);
        quanLy.themCongThuc(caramelMachiatoDaXay);
        quanLy.themCongThuc(traDaoCamSa);
        quanLy.themCongThuc(traMangCau);
        quanLy.themCongThuc(traOiHong);
        quanLy.themCongThuc(traDuaChanhLeo);
        quanLy.themCongThuc(traVai);
        quanLy.themCongThuc(sinhToXoai);
        quanLy.themCongThuc(sinhToDau);
        quanLy.themCongThuc(sinhToBo);
        quanLy.themCongThuc(sinhToMangCau);

        // ===== TỒN KHO BAN ĐẦU (1 lô mỗi nguyên liệu, HSD đa dạng) =====
        LocalDate homNay = LocalDate.now();
        int stt = 1;
        quanLy.nhapKho(new LoNhap("NL" + (stt++), traDen, 1500, homNay.plusDays(20)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), traOLong, 1200, homNay.plusDays(25)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), traLai, 1200, homNay.plusDays(25)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), suaTuoi, 2000, homNay.plusDays(10)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), suaDac, 1000, homNay.plusDays(60)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), duongNuoc, 1000, homNay.plusDays(45)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), daXay, 3000, homNay.plusDays(90)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), espresso, 100, homNay.plusDays(15)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), nuocNong, 5000, homNay.plusDays(365)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), caPhePhin, 2000, homNay.plusDays(60)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), coldBrewCoffee, 1500, homNay.plusDays(14)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), cotDua, 800, homNay.plusDays(20)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), caramelSyrup, 800, homNay.plusDays(90)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), nuocCotChanhVang, 500, homNay.plusDays(8)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), muiBuoi, 600, homNay.plusDays(6)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), siroMo, 700, homNay.plusDays(90)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), nuocCamTuoi, 800, homNay.plusDays(4)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), daoMieng, 800, homNay.plusDays(7)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), saTuoi, 300, homNay.plusDays(6)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), mangCau, 700, homNay.plusDays(5)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), oiHong, 600, homNay.plusDays(5)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), duaMieng, 600, homNay.plusDays(9)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), chanhLeo, 500, homNay.plusDays(12)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), vai, 500, homNay.plusDays(4)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), xoai, 900, homNay.plusDays(5)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), dauTay, 700, homNay.plusDays(3)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), bo, 600, homNay.plusDays(6)));
        quanLy.nhapKho(new LoNhap("NL" + (stt++), suaChua, 800, homNay.plusDays(12)));
    }
}