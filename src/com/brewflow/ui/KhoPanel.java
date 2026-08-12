package com.brewflow.ui;

import com.brewflow.inventory.LoNhap;
import com.brewflow.model.NguyenLieu;
import com.brewflow.service.QuanLyBrewFlow;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class KhoPanel extends JPanel {
    private QuanLyBrewFlow quanLy;
    private DefaultTableModel modelTonKho;
    private DefaultTableModel modelCanhBao;
    private Set<NguyenLieu> tapNguyenLieu;
    private int soThuTuLo = 0;

    public KhoPanel(QuanLyBrewFlow quanLy) {
        this.quanLy = quanLy;
        setLayout(new GridLayout(2, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bảng tổng tồn kho
        modelTonKho = new DefaultTableModel(new String[]{"Nguyên liệu", "Tổng tồn kho", "Đơn vị"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable bangTonKho = new JTable(modelTonKho);
        JPanel panelTonKho = new JPanel(new BorderLayout());
        panelTonKho.setBorder(BorderFactory.createTitledBorder("Tổng tồn kho hiện tại"));
        panelTonKho.add(new JScrollPane(bangTonKho), BorderLayout.CENTER);

        JButton nutLamMoi = new JButton("Làm mới");
        nutLamMoi.addActionListener(e -> lamMoi());

        JButton nutNhapKho = new JButton("Nhập kho...");
        nutNhapKho.addActionListener(e -> moDialogNhapKho());

        JPanel panelNut = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelNut.add(nutLamMoi);
        panelNut.add(nutNhapKho);
        panelTonKho.add(panelNut, BorderLayout.SOUTH);

        // Bảng cảnh báo HSD
        modelCanhBao = new DefaultTableModel(new String[]{"Mã lô", "Nguyên liệu", "Còn lại", "Hạn sử dụng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable bangCanhBao = new JTable(modelCanhBao);
        JPanel panelCanhBao = new JPanel(new BorderLayout());
        panelCanhBao.setBorder(BorderFactory.createTitledBorder("Cảnh báo sắp hết hạn (trong 5 ngày)"));
        panelCanhBao.add(new JScrollPane(bangCanhBao), BorderLayout.CENTER);

        add(panelTonKho);
        add(panelCanhBao);

        lamMoi();
    }

    public void lamMoi() {
        modelTonKho.setRowCount(0);
        // Lấy danh sách nguyên liệu duy nhất từ các công thức hiện có
        tapNguyenLieu = new LinkedHashSet<>();
        quanLy.getDanhSachCongThuc().forEach(ct ->
                ct.getDanhSachThanhPhan().forEach(tp -> tapNguyenLieu.add(tp.getNguyenLieu())));

        for (NguyenLieu nl : tapNguyenLieu) {
            modelTonKho.addRow(new Object[]{
                    nl.getTenNguyenLieu(), quanLy.layTongTonKho(nl), nl.getDonViDo()
            });
        }

        modelCanhBao.setRowCount(0);
        for (LoNhap lo : quanLy.canhBaoSapHetHan(5)) {
            modelCanhBao.addRow(new Object[]{
                    lo.getMaLo(), lo.getNguyenLieu().getTenNguyenLieu(),
                    lo.getSoLuongConLai() + " " + lo.getNguyenLieu().getDonViDo(),
                    lo.getHanSuDung()
            });
        }
    }

    private void moDialogNhapKho() {
        if (tapNguyenLieu == null || tapNguyenLieu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có nguyên liệu nào (cần có công thức trước).");
            return;
        }

        JComboBox<NguyenLieu> comboNguyenLieu = new JComboBox<>(tapNguyenLieu.toArray(new NguyenLieu[0]));
        JTextField oSoLuong = new JTextField();
        JTextField oHanSuDung = new JTextField(LocalDate.now().plusMonths(1).toString());

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.add(new JLabel("Nguyên liệu:"));
        form.add(comboNguyenLieu);
        form.add(new JLabel("Số lượng nhập:"));
        form.add(oSoLuong);
        form.add(new JLabel("Hạn sử dụng (yyyy-MM-dd):"));
        form.add(oHanSuDung);

        int ketQua = JOptionPane.showConfirmDialog(this, form, "Nhập kho",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ketQua != JOptionPane.OK_OPTION) return;

        NguyenLieu nguyenLieuChon = (NguyenLieu) comboNguyenLieu.getSelectedItem();
        double soLuong;
        LocalDate hanSuDung;

        try {
            soLuong = Double.parseDouble(oSoLuong.getText().trim());
            if (soLuong <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số dương hợp lệ.",
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            hanSuDung = LocalDate.parse(oHanSuDung.getText().trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Hạn sử dụng phải theo định dạng yyyy-MM-dd (VD: 2026-08-15).",
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            return;
        }

        soThuTuLo++;
        String maLo = "NK" + String.format("%03d", soThuTuLo);
        quanLy.nhapKho(new LoNhap(maLo, nguyenLieuChon, soLuong, hanSuDung));

        lamMoi();
        JOptionPane.showMessageDialog(this, "Đã nhập kho lô " + maLo + " thành công.");
    }
}