package com.brewflow.service;

import com.brewflow.model.CongThucPhaChe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp quản lý, tương ứng RoomManagement trong mẫu của giảng viên:
 * implements IQuanLy, sở hữu (composition) danh sách CongThucPhaChe.
 * Bổ sung thêm luuFile()/taiFile() - áp dụng Serializable đã khai báo
 * ở CongThucPhaChe để lưu/đọc dữ liệu ra file thật, đúng ví dụ
 * "Ghi các đối tượng được chuỗi hóa" đã học.
 */
public class QuanLyBrewFlow implements IQuanLy {
    private List<CongThucPhaChe> danhSachCongThuc = new ArrayList<>();

    @Override
    public void themCongThuc(CongThucPhaChe congThuc) {
        danhSachCongThuc.add(congThuc);
    }

    @Override
    public boolean xoaCongThuc(String maCongThuc) {
        return danhSachCongThuc.removeIf(ct -> ct.getMaCongThuc().equals(maCongThuc));
    }

    @Override
    public List<CongThucPhaChe> layDanhSach() {
        return danhSachCongThuc;
    }

    /**
     * Ghi toàn bộ danh sách công thức (đã chuỗi hóa - serialized) ra
     * 1 file nhị phân. Chỉ hoạt động được vì CongThucPhaChe (và các
     * lớp con) implements Serializable.
     */
    public void luuFile(String duongDan) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(duongDan))) {
            oos.writeObject(danhSachCongThuc);
        }
    }

    /**
     * Đọc lại danh sách công thức từ file đã lưu bằng luuFile().
     */
    @SuppressWarnings("unchecked")
    public void taiFile(String duongDan) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(duongDan))) {
            danhSachCongThuc = (List<CongThucPhaChe>) ois.readObject();
        }
    }
}
