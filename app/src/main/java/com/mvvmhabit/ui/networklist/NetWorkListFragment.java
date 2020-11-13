package com.mvvmhabit.ui.networklist;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.FragmentNetworkListBinding;
import com.mvvmhabit.app.AppViewModelFactory;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class NetWorkListFragment extends BaseFragment<FragmentNetworkListBinding,NetWorkListViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_network_list;
    }
    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public NetWorkListViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,factory).get(NetWorkListViewModel.class);
    }

    @Override
    public void initData() {
      binding.setAdapter(new BindingRecyclerViewAdapter());
      viewModel.getDataByNetWork();
    }

    @Override
    public void initViewObservable() {
        viewModel.ui.finishRefresh.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
               binding.refresh.finishRefreshing();
            }
        });
        viewModel.ui.finishLoadMore.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                binding.refresh.finishLoadmore();
            }
        });
    }
}
