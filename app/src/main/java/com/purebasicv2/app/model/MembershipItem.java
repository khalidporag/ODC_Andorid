package com.purebasicv2.app.model;

public class MembershipItem {

    private int id;
    private String name;
    private String amount;
    private String type;
    private String graduation;
    private String duration;

    public MembershipItem(int id, String name, String amount, String type, String graduation, String duration) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.graduation = graduation;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getGraduation() {
        return graduation;
    }

    public String getDuration() {
        return duration;
    }
}
