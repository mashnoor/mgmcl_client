package net.rajit.mgmclclient;

import com.google.gson.annotations.SerializedName;

public class Price {
    @SerializedName("head")
    private String head;

    @SerializedName("value")
    private String value;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
