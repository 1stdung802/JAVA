package com.brewflow.ui;

import com.brewflow.model.CongThucPhaChe;
import com.brewflow.model.ThanhPhanCongThuc;
import com.brewflow.service.QuanLyBrewFlow;
import java.awt.*;
import javax.swing.*;

public class CongThucPanel extends JPanel {

    public CongThucPanel(QuanLyBrewFlow quanLy) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultListModel<CongThucPhaChe> modelDanhSach = new DefaultListModel<>();
        quanLy.getDanhSachCongThuc().forEach(modelDanhSach::addElement);
        JList<CongThucPhaChe> danhSachCongThuc = new JList<>(modelDanhSach);
        danhSachCongThuc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTextArea chiTiet = new JTextArea();
        chiTiet.setEditable(false);
        chiTiet.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        danhSachCongThuc.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            CongThucPhaChe ct = danhSachCongThuc.getSelectedValue();
            if (ct == null) return;

            StringBuilder sb = new StringBuilder();
            sb.append("Tên đồ uống: ").append(ct.getTenDoUong()).append("\n");
            sb.append("Loại: ").append(ct.getLoaiDoUong()).append("\n\n");
            sb.append("Thành phần (định lượng chuẩn size M):\n");
            for (ThanhPhanCongThuc tp : ct.getDanhSachThanhPhan()) {
                sb.append("  - ").append(tp).append("\n");
            }
            chiTiet.setText(sb.toString());
        });

        JPanel panelTrai = new JPanel(new BorderLayout());
        panelTrai.setBorder(BorderFactory.createTitledBorder("Danh sách công thức"));
        panelTrai.add(new JScrollPane(danhSachCongThuc), BorderLayout.CENTER);
        panelTrai.setPreferredSize(new Dimension(220, 0));

        JPanel panelPhai = new JPanel(new BorderLayout());
        panelPhai.setBorder(BorderFactory.createTitledBorder("Chi tiết công thức"));
        panelPhai.add(new JScrollPane(chiTiet), BorderLayout.CENTER);

        add(panelTrai, BorderLayout.WEST);
        add(panelPhai, BorderLayout.CENTER);

        if (!modelDanhSach.isEmpty()) {
            danhSachCongThuc.setSelectedIndex(0);
        }
    }
}
