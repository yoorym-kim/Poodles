package org.techtown.poodles.data;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("type")
    String type = "json_object";

    public ResponseModel() {

    }
    public ResponseModel(String type) {
        this.type = type;
    }
}
