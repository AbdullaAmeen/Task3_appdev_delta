package com.example.pawsome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    private String id;
    private String url;
    private String width;
    private Integer height;
    @SerializedName("original_finalname")
    @Expose
    private  String originalFilename;
    private Integer pending;
    private Integer approved;

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public Integer getPending() {
        return pending;
    }

    public Integer getApproved() {
        return approved;
    }
}

