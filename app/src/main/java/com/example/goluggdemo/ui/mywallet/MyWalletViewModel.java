package com.example.goluggdemo.ui.mywallet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyWalletViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyWalletViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my wallet fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}