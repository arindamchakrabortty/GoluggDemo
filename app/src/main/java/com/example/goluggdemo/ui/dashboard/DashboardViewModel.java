package com.example.goluggdemo.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {


    private MutableLiveData<String> mText=new MutableLiveData<>();

    private void setData(String item){
        mText.setValue(item);
    }

    public LiveData<String> getText() {
        return mText;
    }
}