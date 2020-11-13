package com.mvvmhabit.ui.register;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;


import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.ActivityRegisterBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding,RegisterViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.registerviewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.ui.singleLiveEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(viewModel.ui.singleLiveEvent.getValue()){
                    binding.ivShowPassword.setImageResource(R.mipmap.show_psw);
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    binding.ivShowPassword.setImageResource(R.mipmap.show_psw_press);
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}
