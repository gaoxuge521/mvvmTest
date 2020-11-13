package com.mvvmhabit.jiemai.address;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.ActivityAddressAddUpdateBinding;
import com.mvvmhabit.app.AppViewModelFactory;
import com.mvvmhabit.entity.AddressEntity;
import com.mvvmhabit.utils.GsonUtil;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

public class AddressAddUpdateActivity extends BaseActivity<ActivityAddressAddUpdateBinding,AddressAddUpdateViewModel> {
    public static final String ADDREDDJSON = "ADDREDDJSON";
    private AddressEntity entity;

    @Override
    public void initParam() {
        if(getIntent()!=null){
            String stringExtra = getIntent().getStringExtra(ADDREDDJSON);
            if(!TextUtils.isEmpty(stringExtra)){
                entity =   GsonUtil.GsonToObject(stringExtra,AddressEntity.class);
            }
        }
        if(entity==null){
            entity = new AddressEntity();
        }
    }

    @Override
    public AddressAddUpdateViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,factory).get(AddressAddUpdateViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_address_add_update;
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public void initData() {
        setSupportActionBar(binding.include.toolbar);
        viewModel.initToolBar();
        viewModel.setEntity(entity);
        viewModel.initRightText();
    }

    @Override
    public void initViewObservable() {
        viewModel.isDefault.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                KLog.e("sss  "+aBoolean);
                binding.ivIsDefaule.setImageResource(aBoolean?R.drawable.icon_select_pre:R.drawable.icon_select_nor4);
                viewModel.setIsDefault(aBoolean);
            }
        });
    }
}
