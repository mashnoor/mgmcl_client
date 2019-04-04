package net.rajit.mgmclclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.rajit.mgmclclient.R;
import com.rajit.mgmclclient.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ProgressDialog dialog;

    List<String> stoneNames;
    List<Integer> stoneIds;

    RadioGroup rgClientType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.etStoneQuantity.getWindowToken(), 0);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Getting data. Please wait...");
        Logger.addLogAdapter(new AndroidLogAdapter());
        rgClientType = binding.rgClientType;

        getStones();
    }

    private void getStones() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(APILinks.GET_STONES, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Gson gson = new Gson();
                StoneResponse stoneResponse = gson.fromJson(response, StoneResponse.class);
                List<Stone> stones = stoneResponse.getStones();
                stoneNames = new ArrayList<>();
                stoneIds = new ArrayList<>();
                for (Stone s : stones) {
                    stoneNames.add(s.getName());
                    stoneIds.add(s.getId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        MainActivity.this, android.R.layout.simple_spinner_item, stoneNames);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                binding.spnrStoneList.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dialog.dismiss();
                showMsg("Error!", "Something went wrong while fetching stone list.");


            }
        });

    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void getPricing(View v) {
        String clientType, stoneId, quantity;
        if (rgClientType.getCheckedRadioButtonId() == binding.rbConsumer.getId())
            clientType = "0";
        else if (rgClientType.getCheckedRadioButtonId() == binding.rbDealer.getId())
            clientType = "1";
        else {
            showMsg("Error!", "Select a consumer type");
            return;
        }

        quantity = binding.etStoneQuantity.getText().toString();
        if (quantity.isEmpty()) {
            showMsg("Error!", "Enter quantity");
            return;
        }

        stoneId = stoneIds.get(binding.spnrStoneList.getSelectedItemPosition()).toString();

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(APILinks.getPriceLing(stoneId, quantity, clientType), new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                dialog.show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Logger.d(response);
                dialog.dismiss();
                List<Price> allPrices = new ArrayList<>();
                Gson gson = new Gson();
                PricingResponse pricingResponse = gson.fromJson(response, PricingResponse.class);
                allPrices.addAll(pricingResponse.getData().getPrice());
                allPrices.addAll(pricingResponse.getData().getNotes());
                PriceAdapter adapter = new PriceAdapter(allPrices);
                binding.rvPricing.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.rvPricing.setAdapter(adapter);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dialog.dismiss();
                showMsg("Error", "Something went wrong! Try again");

            }
        });


    }

    private void showMsg(String title, String msg) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })

                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
