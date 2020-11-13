package com.mvvmhabit.jiemai;



import android.app.Application;

import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.FlashCartEntity;
import com.mvvmhabit.utils.Contans;
import com.mvvmhabit.utils.GsonUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MainViewModel extends BaseViewModel<DemoRepository> {
    public MainViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
    }

    @Override
    public void onResume() {
        getFlashCart();
        super.onResume();
    }

    public void getFlashCart(){
        model.flashCart()
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(MainViewModel.this)
                .subscribe(new DisposableObserver<BaseResponse<FlashCartEntity>>() {
                    @Override
                    public void onNext(BaseResponse<FlashCartEntity> response) {
                        if(response.getResult()!=null && response.getResult().getCart()!=null){
                            if(response.getResult()!=null && response.getResult().getItems()!=null){
                                SPUtils.getInstance().saveFlashCart(GsonUtil.GsonToString(response.getResult()));
                            }else{
                                SPUtils.getInstance().saveFlashCart("");
                            }
                            Messenger.getDefault().send(response.getResult().getCart().getItems_count()+"", Contans.FLASHCART_NUM);
                            Messenger.getDefault().send(response.getResult(),Contans.GOODDETAIL_CART);
                        }else{
                            Messenger.getDefault().send("0",Contans.FLASHCART_NUM);
                            Messenger.getDefault().send("",Contans.GOODDETAIL_CART);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("sss   "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
