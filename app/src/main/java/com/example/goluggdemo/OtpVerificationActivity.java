package com.example.goluggdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.goluggdemo.databinding.ActivityOtpVerificationBinding;
import com.example.goluggdemo.ui.dashboard.DashboardFragment;
import com.example.goluggdemo.ui.dashboard.DashboardViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class OtpVerificationActivity extends AppCompatActivity {

    ActivityOtpVerificationBinding binding;

    String otp_url="https://phpwebdevelopmentservices.com/development/test/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding=ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String otp=getIntent().getStringExtra("otp");
        String phone=getIntent().getStringExtra("phone");
        binding.OtpPin.setText(otp);


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("mobile",phone);
                    jsonObject.put("mobile_otp",otp);
                    jsonObject.put("firebase_token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                JSONObject jsonObject1=new JSONObject();
                try {
                    jsonObject1.put("jsonrpc","2.0");
                    jsonObject1.put("params",jsonObject);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,
                        otp_url, jsonObject1, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("result")){
                                Toast.makeText(OtpVerificationActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                String fname=response.getJSONObject("result").getJSONObject("userdata")
                                        .optString("first_name");
                                String lname=response.getJSONObject("result").getJSONObject("userdata")
                                        .optString("last_name");
                                String name=fname+" "+lname;
                                Intent intent=new Intent(getApplicationContext(),NavDrawerActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
            }
        });
        getSupportActionBar().hide();
    }
}