package com.brewflow.strategy;

public class Khuay implements PhuongPhapPhaChe {
    @Override
    public String thucHienPha(String tenDoUong) {
        return "Đang khuấy cho: " + tenDoUong + "...";
    }
}
