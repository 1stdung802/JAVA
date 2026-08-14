package com.brewflow.ui;

import com.brewflow.model.*;
import com.brewflow.service.QuanLyBrewFlow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * Cửa sổ chính - tương ứng MainFrame trong mẫu giảng viên: "Use"
 * QuanLyBrewFlow (không kế thừa, chỉ tham chiếu tới). Toàn bộ ứng
 * dụng gói gọn trong 1 màn hình duy nhất, giữ đúng tinh thần tối
 * giản như ví dụ Room được minh họa.
 */
public class BrewFlowGUI extends JFrame {
    private QuanLyBrewFlow quanLy = new QuanLyBrewFlow();
    private DefaultTableModel model;
    private JTable bang;

    public BrewFlowGUI() {
        super("BrewFlow - Quản lý công thức pha chế");
        napDuLieuMau();

        model = new DefaultTableModel(new String[]{"Mã", "Tên đồ uống", "Loại (lớp)", "Giá", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        bang = new JTable(model);
        lamMoiBang();

        JButton nutThem = new JButton("Thêm...");
        nutThem.addActionListener(e -> moDialogThem());

        JButton nutXoa = new JButton("Xóa");
        nutXoa.addActionListener(e -> xoaDongDangChon());

        JButton nutHienThi = new JButton("Hiển thị thông tin");
        nutHienThi.addActionListener(e -> hienThiDongDangChon());

        JButton nutLuu = new JButton("Lưu file...");
        nutLuu.addActionListener(e -> luuFile());

        JButton nutTai = new JButton("Tải file...");
        nutTai.addActionListener(e -> taiFile());

        JPanel panelNut = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelNut.add(nutThem);
        panelNut.add(nutXoa);
        panelNut.add(nutHienThi);
        panelNut.add(nutLuu);
        panelNut.add(nutTai);

        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(bang), BorderLayout.CENTER);
        add(panelNut, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
    }

    /** Dữ liệu mẫu ban đầu - đại diện 4 lớp con, mỗi lớp 2 món. */
    private void napDuLieuMau() {
        quanLy.themCongThuc(new CaPhe("CF01", "Cà phê đen", 20000, "Đang bán", 1));
        quanLy.themCongThuc(new CaPhe("CF02", "Cà phê sữa", 25000, "Đang bán", 2));
        quanLy.themCongThuc(new TraSua("TS01", "Trà sữa trân châu", 30000, "Đang bán", true));
        quanLy.themCongThuc(new TraSua("TS02", "Hồng trà sữa", 28000, "Đang bán", false));
        quanLy.themCongThuc(new TraTraiCay("TC01", "Trà đào cam sả", 32000, "Đang bán", "Đào"));
        quanLy.themCongThuc(new TraTraiCay("TC02", "Trà vải", 30000, "Đang bán", "Vải"));
        quanLy.themCongThuc(new SinhTo("ST01", "Sinh tố xoài", 28000, "Đang bán", true));
        quanLy.themCongThuc(new SinhTo("ST02", "Sinh tố bơ", 32000, "Ngừng bán", false));
    }

    private void lamMoiBang() {
        model.setRowCount(0);
        for (CongThucPhaChe ct : quanLy.layDanhSach()) {
            model.addRow(new Object[]{
                    ct.getMaCongThuc(), ct.getTenDoUong(), ct.getClass().getSimpleName(),
                    String.format("%,.0f đ", ct.tinhGia()), ct.getTrangThai()
            });
        }
    }

    private void moDialogThem() {
        String[] loaiOptions = {"CaPhe", "TraSua", "TraTraiCay", "SinhTo"};
        String loai = (String) JOptionPane.showInputDialog(this, "Chọn loại đồ uống:",
                "Thêm công thức", JOptionPane.PLAIN_MESSAGE, null, loaiOptions, loaiOptions[0]);
        if (loai == null) return;

        JTextField oMa = new JTextField();
        JTextField oTen = new JTextField();
        JTextField oGia = new JTextField();
        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.add(new JLabel("Mã công thức:"));
        form.add(oMa);
        form.add(new JLabel("Tên đồ uống:"));
        form.add(oTen);
        form.add(new JLabel("Giá cơ bản:"));
        form.add(oGia);

        int ok = JOptionPane.showConfirmDialog(this, form, "Thêm " + loai, JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            String ma = oMa.getText().trim();
            String ten = oTen.getText().trim();
            double gia = Double.parseDouble(oGia.getText().trim());

            CongThucPhaChe ctMoi;
            switch (loai) {
                case "CaPhe":
                    ctMoi = new CaPhe(ma, ten, gia, "Đang bán", 1);
                    break;
                case "TraSua":
                    ctMoi = new TraSua(ma, ten, gia, "Đang bán", true);
                    break;
                case "TraTraiCay":
                    ctMoi = new TraTraiCay(ma, ten, gia, "Đang bán", "Đào");
                    break;
                default:
                    ctMoi = new SinhTo(ma, ten, gia, "Đang bán", true);
            }
            quanLy.themCongThuc(ctMoi);
            lamMoiBang();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá phải là số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaDongDangChon() {
        int dong = bang.getSelectedRow();
        if (dong < 0) {
            JOptionPane.showMessageDialog(this, "Chọn 1 dòng trước đã.");
            return;
        }
        String ma = (String) model.getValueAt(dong, 0);
        quanLy.xoaCongThuc(ma);
        lamMoiBang();
    }

    private void hienThiDongDangChon() {
        int dong = bang.getSelectedRow();
        if (dong < 0) {
            JOptionPane.showMessageDialog(this, "Chọn 1 dòng trước đã.");
            return;
        }
        String ma = (String) model.getValueAt(dong, 0);
        List<CongThucPhaChe> ds = quanLy.layDanhSach();
        for (CongThucPhaChe ct : ds) {
            if (ct.getMaCongThuc().equals(ma)) {
                // Gọi qua interface IHienThi - đa hình: mỗi lớp con tự
                // tính tinhGia() khác nhau dù cùng gọi 1 phương thức.
                JOptionPane.showMessageDialog(this, ct.hienThiThongTin(), "Thông tin", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }

    private void luuFile() {
        try {
            quanLy.luuFile("brewflow_data.dat");
            JOptionPane.showMessageDialog(this, "Đã lưu vào brewflow_data.dat");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void taiFile() {
        try {
            quanLy.taiFile("brewflow_data.dat");
            lamMoiBang();
            JOptionPane.showMessageDialog(this, "Đã tải lại dữ liệu từ brewflow_data.dat");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BrewFlowGUI().setVisible(true));
    }
}
