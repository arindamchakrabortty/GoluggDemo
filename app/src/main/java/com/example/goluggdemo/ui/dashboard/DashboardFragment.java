package com.example.goluggdemo.ui.dashboard;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.goluggdemo.NavDrawerActivity;
import com.example.goluggdemo.R;
import com.example.goluggdemo.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment{

   //private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    /*dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);*/

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        /*String fname=getActivity().getIntent().getStringExtra("fname");
        String lname=getActivity().getIntent().getStringExtra("lname");
        String mobile=getActivity().getIntent().getStringExtra("mobile");
        if(fname!=null && lname!=null && mobile != null) {
            binding.tvName.setText(fname + " " + lname);
            binding.tvPhone.setText("+91"+" "+mobile);
        }*/
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE);
        String fname=sharedPreferences.getString("fname",null);
        String lname=sharedPreferences.getString("lname",null);
        TextView tvname=binding.tvName;
        if(fname!=null && lname!=null){
            tvname.setText(fname+" "+lname);

        }





        //final TextView textView = binding.textHome;

       /* dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });*/


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}