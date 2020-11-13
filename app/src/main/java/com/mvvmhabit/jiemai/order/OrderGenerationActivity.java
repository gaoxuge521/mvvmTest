package com.mvvmhabit.jiemai.order;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.ActivityOrderGenerationBinding;
import com.mvvmhabit.app.AppViewModelFactory;
import com.mvvmhabit.entity.OrderGenerationEntity;
import com.mvvmhabit.utils.Contans;
import com.mvvmhabit.utils.GsonUtil;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class OrderGenerationActivity extends BaseActivity<ActivityOrderGenerationBinding,OrderGenerationViewModel> {
    public static final String ORDER_JSON = "ORDER_JSON";
    private OrderGenerationEntity orderGenerationEntity;
    private String addressId;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_order_generation;
    }
    @Override
    public OrderGenerationViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,factory).get(OrderGenerationViewModel.class);
    }

    @Override
    public void initParam() {
        try {
            String stringExtra = getIntent().getStringExtra(ORDER_JSON);
            if(!TextUtils.isEmpty(stringExtra)){
                orderGenerationEntity = GsonUtil.GsonToObject(stringExtra, OrderGenerationEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public void initData() {
       setSupportActionBar(binding.include.toolbar);
       viewModel.initToobar();
       if(orderGenerationEntity!=null){
           viewModel.setOrderGenerationEntity(orderGenerationEntity);
       }
       binding.setAdapter(new BindingRecyclerViewAdapter());
    }

    @Override
    public void initViewObservable() {

        Messenger.getDefault().register(this, Contans.ADDRESS_ID, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                if(!TextUtils.isEmpty(s)){
                    addressId = s;
                    viewModel.initOrderByCart(addressId);
                }
            }
        });
        viewModel.hasAddress .observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    binding.tvCreateNewAddress.setVisibility(View.GONE);
                    binding.tvAddressName.setVisibility(View.VISIBLE);
                    binding.tvAddressAddress.setVisibility(View.VISIBLE);
                }else{
                    binding.tvCreateNewAddress.setVisibility(View.VISIBLE);
                    binding.tvAddressName.setVisibility(View.GONE);
                    binding.tvAddressAddress.setVisibility(View.GONE);
                }
            }
        });
        viewModel.payWay.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    binding.tvOnlinePayments.setBackgroundResource(R.drawable.bg_order_buy);
                    binding.tvOnlinePayments.setTextColor(ContextCompat.getColor(OrderGenerationActivity.this,R.color.white));
                    binding.tvCashOndelivery.setBackgroundResource(R.drawable.bg_order_buy_nor);
                    binding.tvCashOndelivery.setTextColor(ContextCompat.getColor(OrderGenerationActivity.this,R.color.textColor));
                }else{
                    binding.tvOnlinePayments.setBackgroundResource(R.drawable.bg_order_buy_nor);
                    binding.tvOnlinePayments.setTextColor(ContextCompat.getColor(OrderGenerationActivity.this,R.color.textColor));
                    binding.tvCashOndelivery.setBackgroundResource(R.drawable.bg_order_buy);
                    binding.tvCashOndelivery.setTextColor(ContextCompat.getColor(OrderGenerationActivity.this,R.color.white));
                }
            }
        });
    }
}
