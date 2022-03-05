package com.example.goluggdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goluggdemo.databinding.ActivitySignupBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    //String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String fname,lname,email,phone,password,cpassword;
    String jsonresponse;
    public static final String url="https://phpwebdevelopmentservices.com/development/test/api/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //Action Bar hide
        getSupportActionBar().hide();

        String text=binding.tvTermsandCodition.getText().toString();
        SpannableString spannableString=new SpannableString(text);
        ForegroundColorSpan fcs=new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan fcs1=new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(fcs, 44,56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(fcs1,75,89,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.tvTermsandCodition.setText(spannableString);



        binding.textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=binding.fname.getText().toString().trim();
                lname=binding.lname.getText().toString().trim();
                email=binding.emailId.getText().toString().trim();
                phone=binding.phoneNumber.getText().toString().trim();
                password=binding.Password.getText().toString().trim();
                cpassword=binding.cPassword.getText().toString().trim();


                if(!validateFirstName() | !validateLastName() | !validateEmail() | !validatePhoneNumber()
                | !validatePassword() | !validateConfirmPassword()){
                    return;
                }

                registerUser(fname,lname,email,phone,password,cpassword);
               // startActivity(new Intent(getApplicationContext(),OtpVerificationActivity.class));


            }
        });



    }

   private void registerUser(final String fname, final String lname, final String email, final String phone, final String password, final String cpassword) {
       JSONObject jsonObject=new JSONObject();
       try{
           jsonObject.put("first_name",fname);
           jsonObject.put("last_name",lname);
           jsonObject.put("email",email);
           jsonObject.put("phone",phone);
           jsonObject.put("password",password);
           jsonObject.put("password_confirmation",cpassword);
       }
       catch(JSONException e){
           e.printStackTrace();
       }
       JSONObject jsonObject1=new JSONObject();
       try {
           jsonObject1.put("jsonrpc","2.0");
           jsonObject1.put("params",jsonObject);
       }catch (JSONException e){
           e.printStackTrace();
       }

       JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonObject1, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {

               try {
                   if(response.has("error")) {
                       Toast.makeText(SignupActivity.this, response.getJSONObject("error").optString("message"), Toast.LENGTH_LONG).show();
                       return;
                   }
                   if (response.has("result")){
                       Toast.makeText(SignupActivity.this, response.getJSONObject("result").optString("message_2"), Toast.LENGTH_LONG).show();
                       Intent intent=new Intent(getApplicationContext(),OtpVerificationActivity.class);
                       intent.putExtra("otp",response.getJSONObject("result").optString("mobile_otp"));
                       intent.putExtra("phone",jsonObject1.getJSONObject("params").optString("phone"));
                       startActivity(intent);
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
                   Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(SignupActivity.this, error.toString(), Toast.LENGTH_LONG).show();
           }
       });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }



    private boolean validateFirstName(){
        String val=binding.fname.getText().toString().trim();
        if(val.isEmpty()){
            binding.fname.setError("First name required");
            return false;
        }
        else {
            binding.fname.setError(null);
            binding.firstname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateLastName(){
        String val=binding.lname.getText().toString().trim();
        if(val.isEmpty()){
            binding.lname.setError("Last name required");
            return false;
        }
        else {
            binding.lname.setError(null);
            binding.lastname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail(){
        String val=binding.emailId.getText().toString().trim();

        if (!val.isEmpty()){
            if(!Patterns.EMAIL_ADDRESS.matcher(val).matches()){
                binding.emailId.setError("Email format error");
                return false;
            }
            return true;
        }
        else {
            binding.emailId.setError(null);
            binding.email.setErrorEnabled(false);
            return true;
        }
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
        else {
            binding.Password.setError(null);
            binding.password.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateConfirmPassword(){
        String val=binding.cPassword.getText().toString().trim();
        if(val.isEmpty()){
            binding.cPassword.setError("Confirm password required");
            return false;
        }
        else if(!val.equalsIgnoreCase(binding.Password.getText().toString().trim())){
            binding.cPassword.setError("Confirm password not matching with Password ");
            return false;
        }
        else {
            binding.cPassword.setError(null);
            binding.cpassword.setErrorEnabled(false);
            return true;
        }
    }

}