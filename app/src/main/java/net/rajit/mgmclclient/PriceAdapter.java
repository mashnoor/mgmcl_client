package net.rajit.mgmclclient;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rajit.mgmclclient.R;

import java.util.List;

public class PriceAdapter extends BaseQuickAdapter<Price, BaseViewHolder> {
    public PriceAdapter(List<Price> prices) {
        super(R.layout.row_price, prices);
    }

    @Override
    protected void convert(BaseViewHolder holder, Price item) {
        holder.setText(R.id.tvHead, item.getHead());
        holder.setText(R.id.tvValue, item.getValue());

        if (item.getHead().equals("Net Price")) {
            holder.setTextColor(R.id.tvValue, Color.BLACK);
        }

        if (item.getHead().equals("Exchange Rate")) {
            holder.setBackgroundColor(R.id.divider, Color.BLACK);


        }

    }
}
