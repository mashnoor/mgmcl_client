package net.rajit.mgmclclient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PricingResponseData {
    @SerializedName("price")
    private List<Price> price = null;

    @SerializedName("notes")
    private List<Price> notes = null;

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(List<Price> price) {
        this.price = price;
    }

    public List<Price> getNotes() {
        return notes;
    }

    public void setNotes(List<Price> notes) {
        this.notes = notes;
    }


}
