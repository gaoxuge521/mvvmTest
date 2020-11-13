package com.mvvmhabit.jiemai.good;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.ActivityGoodDetailBinding;
import com.mvvmhabit.app.AppViewModelFactory;
import com.mvvmhabit.entity.FlashCartEntity;
import com.mvvmhabit.utils.Contans;
import com.mvvmhabit.utils.CustomDialog;
import com.mvvmhabit.utils.NumberFormatUtils;
import com.mvvmhabit.utils.ScreenSizeUtil;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class GoodDetailActivity extends BaseActivity<ActivityGoodDetailBinding,GoodDetailViewModel> {
    private CustomDialog customDialog;
    private String productId;
    public static final String PRODUCTID = "PRODUCTID";
    private String qty;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_good_detail;
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public void initParam() {

        //获取列表传入的实体
        if(getIntent()!=null){
            Bundle mBundle = getIntent().getExtras();
            if (mBundle != null) {
                productId = mBundle.getString(PRODUCTID);
            }
        }
    }
    public void goTop() {
        binding.scrollView.goTop();
    }
    @Override
    public GoodDetailViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,factory).get(GoodDetailViewModel.class);
    }

    @Override
    public void initData() {

        binding.animBtn.setTextColor(ContextCompat.getColor(this,R.color.white));
        customDialog = new CustomDialog(this);
        //通过binding拿到toolbar控件, 设置给Activity
        setSupportActionBar(binding.includeGood.toolbar);

        //初始化标题
        viewModel.initToobar();
        binding.rvSecond.setAdapter(new BindingRecyclerViewAdapter());
        binding.includeGood.ivRightIcon.setImageResource(R.drawable.collect_nor);
        if(!TextUtils.isEmpty(productId)){
            viewModel.getGoodDetailData(productId);
        }
        Messenger.getDefault().send("", Contans.FLASHCART);
    }


    @Override
    public void initViewObservable() {

        viewModel.cartNumEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String itemId) {
                customDialog.baseDialogForEdit(GoodDetailActivity.this, "请输入数量", "", "取消", "确定", new CustomDialog.NegativeOnClick() {
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
        viewModel.onPagegFirst.observe(this, new Observer<Integer> () {
            @Override
            public void onChanged(@Nullable Integer o) {
                viewModel.setTitleText(getResources().getString(R.string.goods_name));
                startAdnim(binding.includeGood.tvTitle,false);
            }
        });
        viewModel.onPagegNext.observe(this, new Observer<Integer> () {
            @Override
            public void onChanged(@Nullable Integer o) {
                viewModel.setTitleText(getResources().getString(R.string.string_good_detail));
                startAdnim(binding.includeGood.tvTitle,true);
            }
        });

        Messenger.getDefault().register(this, Contans.GOODDETAIL_CART, FlashCartEntity.class, new BindingConsumer<FlashCartEntity>() {
            @Override
            public void call(FlashCartEntity entity) {
                try {
                    if(entity!=null && entity.getItems()!=null){
                        if(entity.getItems().containsKey(productId)){
                            FlashCartEntity.CartItem cartItem = entity.getItems().get(productId);
                            viewModel.itemId.set(cartItem.getItemId());
                            qty = cartItem.getQty();

                        }else{
                            qty ="0";
                        }
                    }
                    if(!TextUtils.isEmpty(qty)){
                        viewModel.onTextChange.set(Integer.valueOf(qty));
                        if(NumberFormatUtils.getInteger(qty,0)==0){
                            viewModel.setIconAddVisible(View.VISIBLE);
                        }else{
                            viewModel.setIconAddVisible(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //设置购物车数量
        Messenger.getDefault().register(this, Contans.FLASHCART_NUM,String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                try {
                    Integer integer = Integer.valueOf(s);
                    viewModel.cartNum.set(integer);
                    binding.msgView.setMessageNumber(integer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //收藏
        viewModel.favoriteEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer==1){
                    binding.includeGood.ivRightIcon.setImageResource(R.drawable.collect_pre);
                }else  if(integer==2){
                    binding.includeGood.ivRightIcon.setImageResource(R.drawable.collect_nor);
                }
            }
        });

    }

    //设置标题动画
    public void startAdnim(View view,boolean isUp){
        TranslateAnimation translateAnimation ;
        if(isUp){
            translateAnimation =  new TranslateAnimation(Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE, ScreenSizeUtil.Dp2Px(this,50),Animation.ABSOLUTE,0);
        }else{
            translateAnimation =  new TranslateAnimation(Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE, -ScreenSizeUtil.Dp2Px(this,50),Animation.ABSOLUTE,0);
        }
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        translateAnimation.setRepeatCount(0);
        translateAnimation.setRepeatMode(Animation.RESTART);
        translateAnimation.setFillAfter(true);
        view.startAnimation(translateAnimation);
    }
}
