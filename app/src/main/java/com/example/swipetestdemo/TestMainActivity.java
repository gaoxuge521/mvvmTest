package com.example.swipetestdemo;

import android.os.Bundle;
import me.goldze.mvvmhabit.base.BaseActivity;


public class TestMainActivity extends BaseActivity {



    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_test_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
