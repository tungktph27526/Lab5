package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout activityMain;
    private Button btnViewProducts;
    private Button btnAddProduct;
    private EditText edTen;
    private EditText edGia;
    private EditText edMota;
    private Button btnThem;
    private Button btnHuy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMain = (LinearLayout) findViewById(R.id.activity_main);
        btnViewProducts = (Button) findViewById(R.id.btnViewProducts);
        btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        btnViewProducts.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), ListProduct.class));
        });
        btnAddProduct.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        edTen = (EditText) dialog.findViewById(R.id.ed_ten);
        edGia = (EditText) dialog.findViewById(R.id.ed_gia);
        edMota = (EditText) dialog.findViewById(R.id.ed_mota);
        btnThem = (Button) dialog.findViewById(R.id.btn_them);
        btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
        btnThem.setOnClickListener(v ->{
            String name = edTen.getText().toString().trim();
            int price = Integer.parseInt(edGia.getText().toString().trim());
            String description = edMota.getText().toString().trim();
            AddProduct(name, price, description);
            dialog.dismiss();
        });
        btnHuy.setOnClickListener(v ->{
            dialog.dismiss();
        });
        dialog.show();
    }
    private void AddProduct(String name, int price, String description) {
        ProductModel model = new ProductModel();
        model.setName(name);
        model.setPrice(price);
        model.setDescription(description);
        Call<ProductModel> call = ApiService.apiService.addProduct(model);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful()) {
                    ProductModel model1 = response.body();
                    //ShowProduct();
                } else {
                    Log.e("API Error", "Failed to fetch products");
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });
    }
}