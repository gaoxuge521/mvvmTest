package com.mvvmhabit.jiemai.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.ActivityLoginJieMaiBinding;
import com.mvvmhabit.app.AppViewModelFactory;
import com.mvvmhabit.utils.Contans;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LoginJieMaiActivity extends BaseActivity<ActivityLoginJieMaiBinding,LoginJieMaiViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login_jie_mai;
    }

    @Override
    public LoginJieMaiViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(LoginJieMaiViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.ui.loginSuccess.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                LoginJieMaiActivity.this.finish();
                Messenger.getDefault().send("", Contans.HOMEREFRESH);
            }
        });
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public void initData() {
        setSupportActionBar(binding.include.toolbar);
        viewModel.initToolBar();
    }
}
