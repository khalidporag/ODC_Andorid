package com.purebasicv2.app.model;

public class ModelTestResultMerit {

    private int SerialNumber;
    private String name;
    private String point;

    public ModelTestResultMerit(int serialNumber, String name, String point) {
        SerialNumber = serialNumber;
        this.name = name;
        this.point = point;
    }

    public int getSerialNumber() {
        return SerialNumber;
    }

    public String getName() {
        return name;
    }

    public String getPoint() {
        return point;
    }
}
