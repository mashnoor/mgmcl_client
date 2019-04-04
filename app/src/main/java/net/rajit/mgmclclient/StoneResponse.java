package net.rajit.mgmclclient;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoneResponse {

    @SerializedName("status")
    private Integer status;

    @SerializedName("data")
    private List<Stone> stones = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Stone> getStones() {
        return stones;
    }

}