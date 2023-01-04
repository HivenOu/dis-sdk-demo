package com.bigdata.dis.sdk.demo.domain.msg;

import com.alibaba.fastjson2.annotation.JSONField;

public class ImageMsg {
    private String type;
    @JSONField(name = "image_data")
    private String imageData;

    public ImageMsg() {
    }

    public ImageMsg(String type, String imageData) {
        this.type = type;
        this.imageData = imageData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}

