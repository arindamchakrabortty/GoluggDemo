package com.example.goluggdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.goluggdemo.databinding.ActivityLoginBinding;
import com.example.goluggdemo.ui.dashboard.DashboardFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    String phone,password,firebase_token;
    String url_login="https://phpwebdevelopmentservices.com/development/test/api/login";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.phone.setPrefixText("+91");
        sharedPreferences=getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        final String type=sharedPreferences.getString("mobile","");
        if (type.isEmpty()){
            Toast.makeText(this, "Please login", Toast.LENGTH_LONG).show();
        }
        else{
            startActivity(new Intent(getApplicationContext(),NavDrawerActivity.class));
        }


        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        binding.textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone=binding.phoneNumber.getText().toString().trim();
                password=binding.Password.getText().toString().trim();
                firebase_token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9";

                if(!validatePhoneNumber() | !validatePassword()){
                    return;
                }
                login_user(phone,password,firebase_token);
            }
        });

    }

    private void login_user(String phone, String password,String firebase_token) {
            JSONObject jsonObject= new JSONObject();
            try {
                jsonObject.put("mobile", phone);
                jsonObject.put("password",password);
                jsonObject.put("firebase_token",firebase_token);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("jsonrpc", "2.0");
                jsonObject1.put("params", jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url_login,
                    jsonObject1, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.has("result")){
                            Intent intent=new Intent(getApplicationContext(),NavDrawerActivity.class);
                            //intent.putExtra("fname",response.getJSONObject("result").getJSONObject("userdata").optString("first_name"));
                           //intent.putExtra("lname",response.getJSONObject("result").getJSONObject("userdata").optString("last_name"));
                           // intent.putExtra("mobile",response.getJSONObject("result").getJSONObject("userdata").optString("mobile"));

                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("mobile",phone);
                            editor.putString("password",password);
                            editor.putString("fname",response.getJSONObject("result").getJSONObject("userdata").optString("first_name"));
                            editor.putString("lname",response.getJSONObject("result").getJSONObject("userdata").optString("last_name"));
                            editor.apply();
                            Toast.makeText(getApplicationContext(), response.getJSONObject("result").optString("message"), Toast.LENGTH_LONG).show();
                            startActivity(intent);

                        }
                        else if(response.has("error")){
                            Toast.makeText(getApplicationContext(),response.getJSONObject("error").optString("message"),Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private boolean validatePhoneNumber(){
        String val=binding.phoneNumber.getText().toString().trim();
        if(val.isEmpty()){
            binding.phoneNumber.setError("Phone number required");
            return false;
        }
        else if(val.length()!=10){
            binding.phoneNumber.setError("Phone number not valid");
            return false;
        }
        /*else if (!val.equalsIgnoreCase(signupActivity.phone)){
            binding.phoneNumber.setError("Phone number not matched");
            return false;
        }*/
        else {
            binding.phoneNumber.setError(null);
            binding.phone.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String val=binding.Password.getText().toString().trim();
        if(val.isEmpty()){
            binding.Password.setError("Password required");
            return false;
        }
        else if(val.length()<6){
            binding.Password.setError("Password length should be minimum of 6");
            return false;
        }
       /* else if (!val.equalsIgnoreCase(signupActivity.password)){
            binding.Password.setError("Password not matched");
            return false;
        }*/
        else {
            binding.Password.setError(null);
            binding.password.setErrorEnabled(false);
            return true;
        }
    }
}