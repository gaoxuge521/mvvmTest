package com.mvvmhabit.jiemai.crad;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.FragmentCardBinding;
import com.mvvmhabit.app.AppViewModelFactory;
import com.mvvmhabit.entity.FlashCartEntity;
import com.mvvmhabit.utils.Contans;
import com.mvvmhabit.utils.CustomDialog;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class CardFragment extends BaseFragment<FragmentCardBinding,CardFmViewModel> {

    private CustomDialog customDialog;
    public static CardFragment newInstance() {
        Bundle args = new Bundle();
        CardFragment fragment = new CardFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_card;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            viewModel.getCartInfo();
        }
    }

    @Override
    public CardFmViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(CardFmViewModel.class);
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public void initData() {
        customDialog = new CustomDialog(getActivity());
        binding.setAdapter(new BindingRecyclerViewAdapter());
        //通过binding拿到toolbar控件, 设置给Activity
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.includelayout.toolbar);
        //初始化标题
        viewModel.initToolbar();
        binding.includelayout.tvRightText.setTextSize(14);

        viewModel.getCartInfo();

    }

    @Override
    public void initViewObservable() {
        //登录和退出登录时 刷新首页数据
        Messenger.getDefault().register(this, Contans.CARTREFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                viewModel.getCartInfo();
            }
        });
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
        Messenger.getDefault().register(this, Contans.GOODDETAIL_CART, FlashCartEntity.class, new BindingConsumer<FlashCartEntity>() {
            @Override
            public void call(FlashCartEntity entity) {
              viewModel.notifyCartNums(entity);
            }
        });
        viewModel.hasGoods.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    binding.rvGoods.setVisibility(View.VISIBLE);
                    binding.llButton.setVisibility(View.VISIBLE);
                    binding.tvNoCartTip.setVisibility(View.GONE);
                }else{
                    binding.rvGoods.setVisibility(View.GONE);
                    binding.llButton.setVisibility(View.GONE);
                    binding.tvNoCartTip.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.onTextChange.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer==0){
                    binding.tvSelectAll.setText(getString(R.string.select_all));
                    binding.ivSelectAll.setImageResource(R.drawable.icon_select_nor);
                }else{
                    binding.tvSelectAll.setText(getString(R.string.un_select_all));
                    binding.ivSelectAll.setImageResource(R.drawable.icon_select_pre);

                }
            }
        });
        viewModel.onSettlementTextChange.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer==0){
                    if(viewModel.getRightText().equals(getString(R.string.edit))){
                        binding.tvSendTip.setVisibility(View.VISIBLE);
                    }else{
                        binding.tvSendTip.setVisibility(View.GONE);
                    }
                    binding.llSettlement.setBackgroundColor(ContextCompat.getColor(viewModel.getApplication(),R.color.color_bg));
                }else{
                    binding.llSettlement.setBackgroundColor(ContextCompat.getColor(viewModel.getApplication(),R.color.bg_color_green));
                    binding.tvSendTip.setVisibility(View.GONE);
                }
            }
        });

        viewModel.editDelChange.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                KLog.e("sss  "+aBoolean);
                if(aBoolean){
                    binding.tvSendTip.setVisibility(View.GONE);
                    binding.tvSettlement.setVisibility(View.GONE);
                    binding.tvDelectCart.setVisibility(View.VISIBLE);
                    viewModel.setRightText(getString(R.string.complete));
                    binding.includelayout.tvRightText.setText(getString(R.string.complete));
                    binding.tvTotalTitle.setVisibility(View.GONE);
                    binding.tvPrice.setVisibility(View.GONE);

                }else{
                    binding.tvSendTip.setVisibility(View.VISIBLE);
                    binding.tvSettlement.setVisibility(View.VISIBLE);
                    binding.tvDelectCart.setVisibility(View.GONE);
                    viewModel.setRightText(getString(R.string.edit));
                    binding.includelayout.tvRightText.setText(getString(R.string.edit));
                    binding.tvTotalTitle.setVisibility(View.VISIBLE);
                    binding.tvPrice.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
