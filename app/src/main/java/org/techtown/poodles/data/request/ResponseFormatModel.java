package org.techtown.poodles.data.request;

import com.google.gson.annotations.SerializedName;

public class ResponseFormatModel {
    @SerializedName("type")
    String type = "json_object";

    public ResponseFormatModel() {

    }
    public ResponseFormatModel(String type) {
        this.type = type;
    }
}
