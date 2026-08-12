package com.brewflow.ui;

import com.brewflow.service.DuLieuMau;
import com.brewflow.service.QuanLyBrewFlow;
import javax.swing.*;

/**
 * Cửa sổ chính của ứng dụng BrewFlow. Chỉ chịu trách nhiệm dựng layout
 * (JTabbedPane gồm 3 tab) - toàn bộ logic nghiệp vụ nằm ở QuanLyBrewFlow
 * và các lớp model/inventory/order/strategy; dữ liệu mẫu nằm ở DuLieuMau.
 */
public class BrewFlowGUI extends JFrame {

    public BrewFlowGUI() {
        super("BrewFlow - Hệ thống quản lý pha chế đồ uống");

        QuanLyBrewFlow quanLy = new QuanLyBrewFlow();
        DuLieuMau.napDuLieuMau(quanLy);

        KhoPanel khoPanel = new KhoPanel(quanLy);
        CongThucPanel congThucPanel = new CongThucPanel(quanLy);
        // Khi đơn hàng được xác nhận pha chế (trừ kho), báo KhoPanel làm mới
        DonHangPanel donHangPanel = new DonHangPanel(quanLy, khoPanel::lamMoi);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Kho nguyên liệu", khoPanel);
        tabbedPane.addTab("Công thức pha chế", congThucPanel);
        tabbedPane.addTab("Đơn hàng", donHangPanel);

        setContentPane(tabbedPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Chạy GUI trên Event Dispatch Thread - quy tắc bắt buộc của Swing
        SwingUtilities.invokeLater(() -> new BrewFlowGUI().setVisible(true));
    }
}