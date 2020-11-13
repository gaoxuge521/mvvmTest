package com.mvvmhabit.jiemai;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.ActivityMain2Binding;
import com.mvvmhabit.app.AppViewModelFactory;
import com.mvvmhabit.jiemai.crad.CardFragment;
import com.mvvmhabit.jiemai.home.HomeFragment;
import com.mvvmhabit.jiemai.me.MeFragment;
import com.mvvmhabit.utils.Contans;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MainActivity extends BaseActivity<ActivityMain2Binding,MainViewModel> {
    private List<Fragment> mFragments;
    private NavigationController navigationController;
    public static final String HOME = "HOME";
    public static final String CART = "CART";

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main2;
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }


    @Override
    public MainViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    @Override
    public void initData() {
        //初始化Fragment
        initFragment();
        //初始化底部Button
        initBottomTab();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(CardFragment.newInstance());
        mFragments.add(MeFragment.newInstance());
        //默认选中第一个
        commitAllowingStateLoss(0);
    }

    private void initBottomTab() {
        navigationController = binding.pagerBottomTabMain.material()
                .addItem(R.drawable.icon_home_nor,R.drawable.icon_home_pre, "首页")
//                .addItem(R.mipmap.huanzhe, "购物车")
                .addItem(R.drawable.icon_cart_nor, R.drawable.icon_cart_pre,"购物车")
                .addItem(R.drawable.icon_me_nor,R.drawable.icon_me_pre, "我的")
                .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
                .build();
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                commitAllowingStateLoss(index);
            }

            @Override
            public void onRepeat(int index) {
            }
        });
    }

    @Override
    public void initParam() {
        super.initParam();
    }


    @Override
    public void initViewObservable() {
        //显示首页
        Messenger.getDefault().register(this, Contans.SHOWHOME,String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                switch (s){
                    case HOME:
                        showHome();
                        break;
                    case CART:
                        showCart();
                        break;
                        default:
                            showHome();
                            break;
                }

            }
        });
        //刷新购物车列表
        Messenger.getDefault().register(this, Contans.FLASHCART,String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                viewModel.getFlashCart();
            }
        });
        //设置购物车数量
        Messenger.getDefault().register(this, Contans.FLASHCART_NUM,String.class, new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                try {
                    Integer integer = Integer.valueOf(s);
                    navigationController.setMessageNumber(1,integer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void showHome(){
        navigationController.setSelect(0);
    }
    public void showCart(){
        navigationController.setSelect(1);
    }
    private void commitAllowingStateLoss(int position) {
        hideAllFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(position + "");
        if (currentFragment != null) {
            transaction.show(currentFragment);
        } else {
            currentFragment = mFragments.get(position);
            transaction.add(R.id.frameLayout, currentFragment, position + "");
        }
        transaction.commitAllowingStateLoss();
    }

    //隐藏所有Fragment
    private void hideAllFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(i + "");
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }
}
