package com.mvvmhabit.ui.register;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class RegisterViewModel extends BaseViewModel {
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> passWord = new ObservableField<>("");
    public ObservableInt clearBtnVisibility = new ObservableInt();

    public UIChangeObservable ui = new UIChangeObservable();
    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> singleLiveEvent  = new SingleLiveEvent<>();
    }
    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    //清楚用户名输入框的内容
    public BindingCommand clearBtnCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Log.e("sss  ","清楚用户名输入框的内容");
            ToastUtils.showShort("清楚用户名输入框的内容");
            userName .set("");
        }
    });
    //显示隐藏密码
    public BindingCommand showPassWordClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Log.e("sss  ","显示隐藏密码");
            ToastUtils.showShort("显示隐藏密码");
            ui.singleLiveEvent.setValue(ui.singleLiveEvent.getValue()==null || !ui.singleLiveEvent.getValue());
        }
    });
    //注册
    public BindingCommand registerClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Log.e("sss  ","注册"+userName.get()+"   "+passWord.get());
            ToastUtils.showShort("注册"+userName.get()+"   "+passWord.get());
        }
    });
    //监听用户名是否输入
    public BindingCommand<Boolean> showBtnClearUserName = new BindingCommand<Boolean>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean aBoolean) {
            if(aBoolean){
                clearBtnVisibility.set(View.VISIBLE);
            }else{
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    }) ;
}