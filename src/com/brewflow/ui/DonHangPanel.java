package com.brewflow.ui;

import com.brewflow.inventory.KhongDuNguyenLieuException;
import com.brewflow.model.CongThucPhaChe;
import com.brewflow.model.Size;
import com.brewflow.order.DonHang;
import com.brewflow.service.QuanLyBrewFlow;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DonHangPanel extends JPanel {
    private QuanLyBrewFlow quanLy;
    private DefaultTableModel modelDon;
    private Runnable khiKhoThayDoi; // callback để KhoPanel làm mới sau khi trừ kho

    public DonHangPanel(QuanLyBrewFlow quanLy, Runnable khiKhoThayDoi) {
        this.quanLy = quanLy;
        this.khiKhoThayDoi = khiKhoThayDoi;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(taoPanelTaoDon(), BorderLayout.NORTH);
        add(taoPanelDanhSachDon(), BorderLayout.CENTER);
        add(taoPanelHanhDong(), BorderLayout.SOUTH);
    }

    private JComboBox<CongThucPhaChe> comboCongThuc;
    private JComboBox<Size> comboSize;

    private JPanel taoPanelTaoDon() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Tạo đơn mới"));

        comboCongThuc = new JComboBox<>(quanLy.getDanhSachCongThuc().toArray(new CongThucPhaChe[0]));
        comboSize = new JComboBox<>(Size.values());
        comboSize.setSelectedItem(Size.M);

        JButton nutTao = new JButton("Tạo đơn");
        nutTao.addActionListener(e -> {
            CongThucPhaChe ct = (CongThucPhaChe) comboCongThuc.getSelectedItem();
            Size size = (Size) comboSize.getSelectedItem();
            if (ct == null) return;
            DonHang don = quanLy.taoDonHang(ct, size);
            lamMoiBangDon();
            JOptionPane.showMessageDialog(this, "Đã tạo đơn " + don.getMaDonHang());
        });

        panel.add(new JLabel("Đồ uống:"));
        panel.add(comboCongThuc);
        panel.add(new JLabel("Size:"));
        panel.add(comboSize);
        panel.add(nutTao);
        return panel;
    }

    private JPanel taoPanelDanhSachDon() {
        modelDon = new DefaultTableModel(new String[]{"Mã đơn", "Đồ uống", "Size", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable bangDon = new JTable(modelDon);
        bangDon.getColumnModel().getColumn(3).setCellRenderer(new TrangThaiCellRenderer());
        this.bangDonHang = bangDon;

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Danh sách đơn hàng (chọn 1 dòng để thao tác)"));
        panel.add(new JScrollPane(bangDon), BorderLayout.CENTER);
        lamMoiBangDon();
        return panel;
    }

    private JTable bangDonHang;

    private JPanel taoPanelHanhDong() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton nutXacNhan = new JButton("Xác nhận pha chế");
        nutXacNhan.addActionListener(e -> thucHien(don -> {
            try {
                quanLy.xacNhanPha(don);
                khiKhoThayDoi.run(); // kho vừa bị trừ -> báo KhoPanel làm mới
            } catch (KhongDuNguyenLieuException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Không đủ nguyên liệu", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Thao tác không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        }));

        JButton nutHoanThanh = new JButton("Hoàn thành");
        nutHoanThanh.addActionListener(e -> thucHien(don -> {
            try {
                quanLy.hoanThanhDon(don);
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Thao tác không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        }));

        JButton nutHuy = new JButton("Hủy đơn");
        nutHuy.addActionListener(e -> thucHien(don -> {
            try {
                quanLy.huyDon(don);
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Thao tác không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        }));

        panel.add(nutXacNhan);
        panel.add(nutHoanThanh);
        panel.add(nutHuy);
        return panel;
    }

    private void thucHien(java.util.function.Consumer<DonHang> hanhDong) {
        int dong = bangDonHang.getSelectedRow();
        if (dong < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 đơn hàng trong danh sách trước.");
            return;
        }
        DonHang don = quanLy.getDanhSachDonHang().get(dong);
        hanhDong.accept(don);
        lamMoiBangDon();
    }

    private void lamMoiBangDon() {
        modelDon.setRowCount(0);
        List<DonHang> danhSach = quanLy.getDanhSachDonHang();
        for (DonHang don : danhSach) {
            modelDon.addRow(new Object[]{
                    don.getMaDonHang(), don.getCongThucPhaChe().getTenDoUong(),
                    don.getSize(), don.getTenTrangThai()
            });
        }
    }

    private static class TrangThaiCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int col) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            String trangThai = String.valueOf(value);
            Color mau;
            switch (trangThai) {
                case "Chờ pha chế":
                    mau = new Color(255, 244, 179); // vàng nhạt
                    break;
                case "Đang pha chế":
                    mau = new Color(198, 224, 255); // xanh dương nhạt
                    break;
                case "Hoàn thành":
                    mau = new Color(198, 239, 206); // xanh lá nhạt
                    break;
                case "Đã hủy":
                    mau = new Color(255, 199, 199); // đỏ nhạt
                    break;
                default:
                    mau = Color.WHITE;
            }
            if (!isSelected) {
                c.setBackground(mau);
            }
            return c;
        }
    }
}