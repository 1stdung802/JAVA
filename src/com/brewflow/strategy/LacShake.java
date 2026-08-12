package com.brewflow.strategy;

public class LacShake implements PhuongPhapPhaChe {
    @Override
    public String thucHienPha(String tenDoUong) {
        return "Đang lắc (shake) cho: " + tenDoUong + "...";
    }
}
