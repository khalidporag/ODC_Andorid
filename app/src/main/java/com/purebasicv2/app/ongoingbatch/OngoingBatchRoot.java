package com.purebasicv2.app.ongoingbatch;

import com.purebasicv2.app.model.OngoingBatch;

import java.util.ArrayList;
import java.util.List;

public class OngoingBatchRoot {

    private Boolean success;
    private String status;
    private Integer error_code;

    private String message;

    public List<OngoingBatch> data = new ArrayList<>();

    public List<OngoingBatch> getData() {
        return data;
    }

    public void setData(List<OngoingBatch> data) {
        this.data = data;
    }
}
