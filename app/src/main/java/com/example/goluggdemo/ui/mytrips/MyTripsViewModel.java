package com.example.goluggdemo.ui.mytrips;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyTripsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyTripsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mytrips fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}