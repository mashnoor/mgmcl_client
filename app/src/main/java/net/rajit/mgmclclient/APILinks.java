package net.rajit.mgmclclient;

public class APILinks {
    public static final String GET_STONES = "http://139.162.34.55/api/get/stones";

    public static String getPriceLing(String stone_id, String quantity, String delaer) {
        return "http://139.162.34.55/api/get/price?stone_id=" + stone_id + "&quantity=" + quantity + "&dealer=" + delaer;
    }
}
