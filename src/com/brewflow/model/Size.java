package com.brewflow.model;

public enum Size {
    S(0.75),
    M(1.0),
    L(1.5);

    private final double heSoNhan;

    Size(double heSoNhan) {
        this.heSoNhan = heSoNhan;
    }

    public double getHeSoNhan() {
        return heSoNhan;
    }
}
