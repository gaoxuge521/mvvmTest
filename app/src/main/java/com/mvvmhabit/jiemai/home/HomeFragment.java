package com.mvvmhabit.jiemai.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.FragmentHomeBinding;
import com.mvvmhabit.app.AppViewModelFactory;
import com.mvvmhabit.entity.FlashCartEntity;
import com.mvvmhabit.utils.Contans;
import com.mvvmhabit.utils.CustomDialog;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeFmViewModel> {
    private CustomDialog customDialog;
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public HomeFmViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(HomeFmViewModel.class);
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public void initData() {
        customDialog = new CustomDialog(getActivity());
        binding.setAdapter(new BindingRecyclerViewAdapter());
        viewModel.getHome(0);
    }

    @Override
    public void initViewObservable() {
        viewModel.cartNumEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String itemId) {
                customDialog.baseDialogForEdit(getActivity(), "请输入数量", "", "取消", "确定", new CustomDialog.NegativeOnClick() {
                    @Override
                    public void onNegativeClick() {

                    }
                }, new CustomDialog.PositiveContentOnClick() {
                    @Override
                    public void onPositiveClick(String content) {
                        KLog.e("sss  "+content);
                        viewModel.updateCart(itemId,"3",content);
                    }
                },true);
            }
        });

        //登录和退出登录时 刷新首页数据
        Messenger.getDefault().register(this, Contans.HOMEREFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
               viewModel.getHome(0);
            }
        });
        Messenger.getDefault().register(this, Contans.GOODDETAIL_CART, FlashCartEntity.class, new BindingConsumer<FlashCartEntity>() {
            @Override
            public void call(FlashCartEntity entity) {
                viewModel.notifyCartNums(entity);
            }
        });
        viewModel.ui.refresh.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                binding.refreshHome.finishRefreshing();
            }
        });
        viewModel.ui.loadMore.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                binding.refreshHome.finishLoadmore();
            }
        });
    }
}
