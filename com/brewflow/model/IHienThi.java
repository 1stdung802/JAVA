package com.brewflow.model;

/**
 * Interface tương ứng IAction trong mẫu của giảng viên: định nghĩa 2
 * hành vi mà mọi loại đồ uống phải có - hiển thị thông tin và tính giá.
 * CongThucPhaChe implements interface này; các lớp con (CaPhe, TraSua,
 * TraTraiCay, SinhTo) override tinhGia() theo công thức riêng.
 */
public interface IHienThi {
    String hienThiThongTin();
    double tinhGia();
}
