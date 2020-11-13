package com.mvvmhabit.jiemai.welcome;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.ActivityWelcomeBinding;

import java.lang.reflect.Method;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding,WelcomeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {

        // 全屏设置,隐藏窗口所有装饰(包括顶部任务栏)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 标题是属于View的,所以窗口所有的修饰部分隐藏后标题依然有效,需要隐藏掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = 0x00000100 | 0x00000200 | 0x00000400;
        try {
            Method method = Window.class.getMethod("addExtraFlags",
                    int.class);
            method.invoke(getWindow(), flag);
        } catch (Exception e) {
            KLog.e("sss", "addExtraFlags not found.");
        }

        return R.layout.activity_welcome;
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.startTimer();
    }
}
