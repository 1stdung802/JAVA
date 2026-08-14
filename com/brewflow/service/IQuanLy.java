package com.brewflow.service;

import com.brewflow.model.CongThucPhaChe;
import java.util.List;

/**
 * Interface tương ứng IManagement trong mẫu của giảng viên: định nghĩa
 * các nghiệp vụ quản lý cơ bản. QuanLyBrewFlow implements interface này.
 */
public interface IQuanLy {
    void themCongThuc(CongThucPhaChe congThuc);
    boolean xoaCongThuc(String maCongThuc);
    List<CongThucPhaChe> layDanhSach();
}
