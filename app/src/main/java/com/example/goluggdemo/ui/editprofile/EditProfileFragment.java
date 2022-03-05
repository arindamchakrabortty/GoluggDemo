package com.example.goluggdemo.ui.editprofile;

import static com.google.android.material.textfield.TextInputLayout.END_ICON_CUSTOM;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.goluggdemo.R;
import com.example.goluggdemo.databinding.FragmentEditprofileBinding;
import com.google.android.material.textfield.TextInputLayout;


public class EditProfileFragment extends Fragment {

    private EditProfileViewModel editProfileViewModel;
    private FragmentEditprofileBinding fragmentEditprofileBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        editProfileViewModel=new ViewModelProvider(this).get(EditProfileViewModel.class);
        fragmentEditprofileBinding=FragmentEditprofileBinding.inflate(inflater,container,false);
        View root=fragmentEditprofileBinding.getRoot();




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentEditprofileBinding=null;
    }
}
