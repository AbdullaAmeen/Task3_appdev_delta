package com.example.pawsome;

public class Label{
   // @JsonProperty("Name")
    public String name;
   // @JsonProperty("Confidence")
    public double confidence;

    public String getName() {
        return name;
    }

    public double getConfidence() {
        return confidence;
    }
}

