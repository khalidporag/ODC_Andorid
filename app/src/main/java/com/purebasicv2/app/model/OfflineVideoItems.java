package com.purebasicv2.app.model;

import java.io.File;

public class OfflineVideoItems {

    private String name;
    private File thumb;

    public OfflineVideoItems(String name, File thumb) {
        this.name = name;
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public File getThumb() {
        return thumb;
    }
}
