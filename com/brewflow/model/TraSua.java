package com.brewflow.model;

/**
 * Kế thừa CongThucPhaChe, tương ứng VipRoom trong mẫu giảng viên:
 * thêm thuộc tính riêng (coTranChau) và override tinhGia() - có trân
 * châu thì cộng thêm phụ phí.
 */
public class TraSua extends CongThucPhaChe {
    private boolean coTranChau;

    public TraSua(String maCongThuc, String tenDoUong, double giaCoBan, String trangThai, boolean coTranChau) {
        super(maCongThuc, tenDoUong, giaCoBan, trangThai);
        this.coTranChau = coTranChau;
    }

    public boolean isCoTranChau() {
        return coTranChau;
    }

    public void setCoTranChau(boolean coTranChau) {
        this.coTranChau = coTranChau;
    }

    @Override
    public double tinhGia() {
        return getGiaCoBan() + (coTranChau ? 5000 : 0);
    }
}
