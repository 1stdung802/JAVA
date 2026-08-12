package com.brewflow.strategy;

public class Xay implements PhuongPhapPhaChe {
    @Override
    public String thucHienPha(String tenDoUong) {
        return "Đang xay (blend) cho: " + tenDoUong + "...";
    }
}
