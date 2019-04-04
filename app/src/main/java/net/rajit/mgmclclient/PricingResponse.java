package net.rajit.mgmclclient;

import com.google.gson.annotations.SerializedName;

public class PricingResponse {
    @SerializedName("status")
    private Integer status;

    @SerializedName("data")
    private PricingResponseData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PricingResponseData getData() {
        return data;
    }

    public void setData(PricingResponseData data) {
        this.data = data;
    }
}
