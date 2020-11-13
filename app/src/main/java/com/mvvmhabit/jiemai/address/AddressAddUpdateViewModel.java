package com.mvvmhabit.jiemai.address;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;


import androidx.databinding.ObservableField;

import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.AddressEntity;
import com.mvvmhabit.entity.SaveAddressEntity;
import com.mvvmhabit.ui.base.viewmodel.ToolbarViewModel;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RegexUtils;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class AddressAddUpdateViewModel extends ToolbarViewModel<DemoRepository> {
    public ObservableField<AddressEntity> entity = new ObservableField<>();
    public SingleLiveEvent<Boolean> isDefault = new SingleLiveEvent<>();
    public void setEntity(AddressEntity addressEntity) {
        this.entity .set(addressEntity);
        isDefault.setValue(entity.get().getIs_default().equals("0")?false:true);
    }

    public AddressAddUpdateViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
    }

    //初始化toolbar
    public void initToolBar(){
        setRightIconVisible(View.GONE);
        setRightTextVisible(View.GONE);
    }
    public void initRightText(){
        if(entity.get()!=null && !TextUtils.isEmpty(entity.get().getId())){
            setRightText("删除");
            setRightTextVisible(View.VISIBLE);
            setTitleText("修改地址");
        }else{
            setRightTextVisible(View.GONE);
            setTitleText("地址编辑");
        }
    }
    //设置默认地址
    public void setIsDefault(boolean isDefault){
        entity.get().setIs_default(isDefault?"1":"0");
    }


    //默认地址点击
    public BindingCommand defauleClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            isDefault.setValue(entity.get().getIs_default().equals("0")?true:false);
        }
    });

    //添加或者修改地址
    public BindingCommand addUpdateClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            addUpdate();
        }
    });

    private void addUpdate() {
        if(TextUtils.isEmpty(entity.get().getAddress_details())){
            ToastUtils.showShort("请输入详细地址~");
            return;
        }
        if(TextUtils.isEmpty(entity.get().getRealname())){
            ToastUtils.showShort("请输入收货人姓名~");
            return;
        }
        if(!RegexUtils.isMobileExact(entity.get().getMobile())){
            ToastUtils.showShort("请输入正确的手机号码~");
            return;
        }
        if(TextUtils.isEmpty(entity.get().getAddress_name())){
            ToastUtils.showShort("请输入收货地址~");
            return;
        }

        model.saveAddress(entity.get().getId(),"海淀区","北京市","116.308464","北京市",entity.get().getAddress_details(),entity.get().getMobile(),entity.get().getIs_default(),"40.037222",entity.get().getRealname())
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AddressAddUpdateViewModel.this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("请求中。。。");
                    }
                }).subscribe(new DisposableObserver<BaseResponse<SaveAddressEntity>>() {

            @Override
            public void onNext(BaseResponse<SaveAddressEntity> saveAddressEntityBaseResponse) {
                if( saveAddressEntityBaseResponse.getResult()!=null && TextUtils.isEmpty(saveAddressEntityBaseResponse.getResult().getError())){
                    finish();
                }else{
                    ToastUtils.showShort(saveAddressEntityBaseResponse.getResult().getError());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                dismissDialog();
            }
        });
    }

    @Override
    protected void rightTextOnClick() {
        removeAddress();
    }

    //删除地址
    private void removeAddress() {
        model.removeAddress(entity.get().getId())
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AddressAddUpdateViewModel.this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("请求中。。。");
                    }
                }).subscribe(new DisposableObserver<BaseResponse<SaveAddressEntity>>() {

            @Override
            public void onNext(BaseResponse<SaveAddressEntity> response) {
                if(response.isOk()){
                    finish();
                }else{
                    ToastUtils.showShort(response.getResult().getError());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                dismissDialog();
            }
        });
    }
}
