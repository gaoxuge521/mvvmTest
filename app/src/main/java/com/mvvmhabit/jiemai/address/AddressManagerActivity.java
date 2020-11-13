package com.mvvmhabit.jiemai.address;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.ActivityAddressManagerBinding;
import com.mvvmhabit.app.AppViewModelFactory;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class AddressManagerActivity extends BaseActivity<ActivityAddressManagerBinding,AddressManagerViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_address_manager;
    }


    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public AddressManagerViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,factory).get(AddressManagerViewModel.class);
    }

    @Override
    public void initData() {
        setSupportActionBar(binding.include.toolbar);
        viewModel.initToolBar();
        binding.setAdapter(new BindingRecyclerViewAdapter());
        viewModel.getAddressList();

    }

    @Override
    public void initViewObservable() {

    }
}
