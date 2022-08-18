package com.purebasicv2.app.model;

import java.util.ArrayList;
import java.util.List;

public class CategoryRoot {

    public List<CategoryItems> data = new ArrayList<>();

    public List<CategoryItems> getData() {
        return data;
    }

    public void setData(List<CategoryItems> data) {
        this.data = data;
    }
}
