package com.example.pawsome.ui.Analyze.AnalysisResponse;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AnalysisResponse {

    @SerializedName("labels")
    @Expose
    private ArrayList<Label> labels;
    @SerializedName("moderation_labels")
    @Expose
    private ArrayList<Object> moderationLabels;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("image_id")
    @Expose
    private String imageId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;


    public ArrayList<Label> getLabels() {
        return labels;
    }

    public ArrayList<Object> getModerationLabels() {
        return moderationLabels;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}