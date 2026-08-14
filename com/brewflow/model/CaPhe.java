package com.brewflow.model;

/**
 * Kế thừa CongThucPhaChe, tương ứng StandardRoom trong mẫu giảng viên:
 * thêm 1 thuộc tính riêng (soShotEspresso) và override tinhGia() theo
 * công thức riêng của cà phê - mỗi shot espresso cộng thêm phụ phí.
 */
public class CaPhe extends CongThucPhaChe {
    private int soShotEspresso;

    public CaPhe(String maCongThuc, String tenDoUong, double giaCoBan, String trangThai, int soShotEspresso) {
        super(maCongThuc, tenDoUong, giaCoBan, trangThai);
        this.soShotEspresso = soShotEspresso;
    }

    public int getSoShotEspresso() {
        return soShotEspresso;
    }

    public void setSoShotEspresso(int soShotEspresso) {
        this.soShotEspresso = soShotEspresso;
    }

    @Override
    public double tinhGia() {
        return getGiaCoBan() + soShotEspresso * 5000;
    }
}
