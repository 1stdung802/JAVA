package com.brewflow.strategy;

public class PhaMay implements PhuongPhapPhaChe {
    @Override
    public String thucHienPha(String tenDoUong) {
        return "Đang pha máy espresso cho: " + tenDoUong + "...";
    }
}
