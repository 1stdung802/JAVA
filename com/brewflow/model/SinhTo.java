package com.brewflow.model;

/**
 * Kế thừa CongThucPhaChe: thêm thuộc tính riêng (coSuaChua) và
 * override tinhGia() - có sữa chua thì cộng thêm phụ phí.
 */
public class SinhTo extends CongThucPhaChe {
    private boolean coSuaChua;

    public SinhTo(String maCongThuc, String tenDoUong, double giaCoBan, String trangThai, boolean coSuaChua) {
        super(maCongThuc, tenDoUong, giaCoBan, trangThai);
        this.coSuaChua = coSuaChua;
    }

    public boolean isCoSuaChua() {
        return coSuaChua;
    }

    public void setCoSuaChua(boolean coSuaChua) {
        this.coSuaChua = coSuaChua;
    }

    @Override
    public double tinhGia() {
        return getGiaCoBan() + (coSuaChua ? 4000 : 0);
    }
}
