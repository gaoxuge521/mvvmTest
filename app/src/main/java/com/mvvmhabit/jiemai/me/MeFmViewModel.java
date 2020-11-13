package com.mvvmhabit.jiemai.me;

import android.app.Application;
import android.graphics.drawable.Drawable;


import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.example.swipetestdemo.R;
import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.LogoutEntity;
import com.mvvmhabit.entity.UserInfoEntity;
import com.mvvmhabit.jiemai.login.LoginJieMaiActivity;
import com.mvvmhabit.utils.Contans;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MeFmViewModel extends BaseViewModel<DemoRepository> {
    public ObservableField<UserInfoEntity> userInfoEntity = new ObservableField<>();
    public SingleLiveEvent out = new SingleLiveEvent();
    public SingleLiveEvent outAvatar = new SingleLiveEvent();
    public Drawable drawable;
    public MeFmViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
        drawable = ContextCompat.getDrawable(application, R.drawable.img_default);
    }


    public  BindingCommand loginClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("登录");
           startActivity(LoginJieMaiActivity.class);
        }
    });
    public  BindingCommand loginOut = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            out.call();
        }
    });


    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    public void getUserInfo(){
        model.getUserInfo()
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(MeFmViewModel.this)
                .subscribe(new DisposableObserver<BaseResponse<UserInfoEntity>>() {
                    @Override
                    public void onNext(BaseResponse<UserInfoEntity> userInfoEntityBaseResponse) {
                        if(userInfoEntityBaseResponse.getMessage().equals(Contans.OK)){
                            userInfoEntity.set(userInfoEntityBaseResponse.getResult());

                        }else{
                            KLog.e("sss   设置头像");
                            userInfoEntity.set(null);
                            outAvatar.call();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("获取个人信息失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //退出登录
    public void logout(){
        model.logout()
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(MeFmViewModel.this)
                .subscribe(new DisposableObserver<BaseResponse<LogoutEntity>>() {
                    @Override
                    public void onNext(BaseResponse<LogoutEntity> userInfoEntityBaseResponse) {
                        if(userInfoEntityBaseResponse.getMessage().equals(Contans.OK)){
                            if(userInfoEntityBaseResponse.getResult().getResult().equals(Contans.OK)){
                                clearToken();
                                outAvatar.call();
                                getUserInfo();
                                Messenger.getDefault().send("", Contans.HOMEREFRESH);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
