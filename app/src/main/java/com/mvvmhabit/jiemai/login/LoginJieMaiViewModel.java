package com.mvvmhabit.jiemai.login;

import android.app.Application;
import android.text.TextUtils;

import androidx.databinding.ObservableField;

import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.UserInfoEntity;
import com.mvvmhabit.ui.base.viewmodel.ToolbarViewModel;
import com.mvvmhabit.utils.Contans;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LoginJieMaiViewModel extends ToolbarViewModel<DemoRepository> {
    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> passWord = new ObservableField<>("");


    public UIchangeObservable ui = new UIchangeObservable();
    public class UIchangeObservable{
        public SingleLiveEvent loginSuccess = new SingleLiveEvent();
    }
    public  BindingCommand loginNetClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("登录");
            login();
        }
    });

    public void initToolBar(){
        setTitleText("账号密码登录");
    }
    public LoginJieMaiViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
        userName.set(model.getUserName());
        passWord.set(model.getPassword());
    }

    public void login(){
        if(TextUtils.isEmpty(userName.get())){
            ToastUtils.showShort("用户名不能为空！");
            return;
        }
        if(TextUtils.isEmpty(passWord.get())){
            ToastUtils.showShort("密码不能为空！");
            return;
        }
//        addSubscribe();
        model.loginByUsernamePwd(userName.get(),passWord.get())
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(LoginJieMaiViewModel.this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("登录中。。。");
                    }
                }).subscribe(new DisposableObserver<BaseResponse<UserInfoEntity>>() {


            @Override
            public void onNext(BaseResponse<UserInfoEntity> userInfoEntityBaseResponse) {
                if(userInfoEntityBaseResponse.getMessage().equals(Contans.OK)){
                    KLog.e("sss   登录成功");
                    model.saveIsLogin(true);
                    model.saveUserName(userName.get());
                    model.savePassword(passWord.get());
                    ui.loginSuccess.call();
                }else{
                    model.saveIsLogin(false);
                    ToastUtils.showShort(userInfoEntityBaseResponse.getResult().getError());
                }
                dismissDialog();
            }

            @Override
            public void onError(Throwable e) {
                dismissDialog();
            }

            @Override
            public void onComplete() {
                dismissDialog();
            }
        });
    }
}
