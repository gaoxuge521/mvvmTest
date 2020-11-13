package com.mvvmhabit.ui.home;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.ActivityHomBinding;
import com.mvvmhabit.app.AppViewModelFactory;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class HomeActivity extends BaseActivity<ActivityHomBinding,HomeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_hom;
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public HomeViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    @Override
    public void initData() {
        binding.setAdapter(new BindingRecyclerViewAdapter());
        viewModel.getHome(0);
    }

    @Override
    public void initViewObservable() {
//        viewModel.ui.refresh.observe(this, new Observer() {
//            @Override
//            public void onChanged(@Nullable Object o) {
//                binding.refresh.finishRefreshing();
//            }
//        });
//        viewModel.ui.loadMore.observe(this, new Observer() {
//            @Override
//            public void onChanged(@Nullable Object o) {
//                binding.refresh.finishLoadmore();
//            }
//        });
    }
}
