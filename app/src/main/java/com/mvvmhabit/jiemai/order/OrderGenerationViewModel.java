package com.mvvmhabit.jiemai.order;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;


import com.example.swipetestdemo.R;
import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.OrderGenerationEntity;
import com.mvvmhabit.entity.SubmitOrderEntity;
import com.mvvmhabit.jiemai.MainActivity;
import com.mvvmhabit.jiemai.address.AddressManagerActivity;
import com.mvvmhabit.ui.base.viewmodel.ToolbarViewModel;
import com.mvvmhabit.utils.Contans;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OrderGenerationViewModel extends ToolbarViewModel<DemoRepository> {
    public ObservableField<String> remark = new ObservableField<>();
    //数据
    public ObservableField<OrderGenerationEntity> orderGenerationEntity = new ObservableField<>();
    ObservableField<String> patMethod = new ObservableField<>("check_money");
    //支付方式
    public SingleLiveEvent<Boolean> payWay = new SingleLiveEvent<Boolean>();
    //商品列表
    public ObservableList<OrderGenerationProductItemViewModel> list = new ObservableArrayList<>();
    public ItemBinding<OrderGenerationProductItemViewModel>  itemBinding = ItemBinding.of(com.example.swipetestdemo.BR.viewModel, R.layout.item_order_product);
    //地址选择
    public SingleLiveEvent<Boolean> hasAddress = new SingleLiveEvent<Boolean>();
    public OrderGenerationViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
    }

    public void setOrderGenerationEntity(OrderGenerationEntity orderGenerationEntity) {
        this.orderGenerationEntity.set(orderGenerationEntity);
        list.clear();
        for (OrderGenerationEntity.NoProductsManjian product : orderGenerationEntity.getProducts()) {
            OrderGenerationProductItemViewModel item  = new OrderGenerationProductItemViewModel(OrderGenerationViewModel.this,product);
            list.add(item);
        }
        //默认货到付款
        payWay.setValue(false);
        setAddress();
        if(!TextUtils.isEmpty(orderGenerationEntity.getDeliveryNote())){
            ToastUtils.showShort(orderGenerationEntity.getDeliveryNote());
        }
    }

    //判断是否有地址
    public  boolean hasAddress(){
        return orderGenerationEntity.get().getAddress()!=null;
    }
    //地址是否存在
    public void setAddress(){
        if( orderGenerationEntity.get().getAddress()!=null){
            hasAddress.setValue(true);
        }else{
            hasAddress.setValue(false);
        }
    }
    public void initToobar(){
        setRightIconVisible(View.GONE);
        setRightTextVisible(View.GONE);
        setTitleText(getApplication().getString(R.string.confirm_order));
    }


    //设置地址
    public BindingCommand addressClick=  new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          startActivity(AddressManagerActivity.class);
        }
    });
    //在线支付
    public BindingCommand onlinePaymentsClick=  new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            patMethod.set("wxpay");
            payWay.setValue(true);
        }
    });
    //货到付款
    public BindingCommand cashOndeliveryClick=  new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            payWay.setValue(false);
            patMethod.set("check_money");
        }
    });


    public BindingCommand submitOrderClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(!hasAddress()){
                ToastUtils.showShort("请选择地址");
                return;
            }
            submitOrder(remark.get(),patMethod.get(),orderGenerationEntity.get().getAddressId());
        }
    });
    //获取订单数据
    public void initOrderByCart(String addressId){
        model.initOrderByCart(addressId)
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(OrderGenerationViewModel.this)
                .subscribe(new DisposableObserver<BaseResponse<OrderGenerationEntity>>() {
                    @Override
                    public void onNext(BaseResponse<OrderGenerationEntity> response) {
                      if(response.isOk()){
                          setOrderGenerationEntity(response.getResult());
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
    //获取订单数据
    public void submitOrder(String orderRemark,String payMethod,String addressId){
        model.submitOrder(orderRemark,payMethod,addressId)
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(OrderGenerationViewModel.this)
                .subscribe(new DisposableObserver<BaseResponse<SubmitOrderEntity>>() {
                    @Override
                    public void onNext(BaseResponse<SubmitOrderEntity> response) {
                      if(response.isOk()){
                          Messenger.getDefault().send("", Contans.CARTREFRESH);
                          ToastUtils.showShort("提交成功！");
                          startActivity(MainActivity.class);
                      }else{
                          ToastUtils.showShort(response.getResult().getError());
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
