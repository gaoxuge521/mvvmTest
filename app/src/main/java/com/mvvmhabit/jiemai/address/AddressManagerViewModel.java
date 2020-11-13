package com.mvvmhabit.jiemai.address;

import android.app.Application;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;


import com.example.swipetestdemo.R;
import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.AddressEntity;
import com.mvvmhabit.entity.AddressListEntity;
import com.mvvmhabit.ui.base.viewmodel.ToolbarViewModel;
import com.mvvmhabit.utils.GsonUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AddressManagerViewModel extends ToolbarViewModel<DemoRepository> {
    public ObservableList<AddressItemViewModel> list = new ObservableArrayList<>();
    public ItemBinding<AddressItemViewModel> itemBinding = ItemBinding.of(com.example.swipetestdemo.BR.viewModel, R.layout.item_address);

    public AddressManagerViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
    }


    public void initToolBar(){
        setRightIconVisible(View.GONE);
        setRightTextVisible(View.GONE);
        setTitleText("收货地址管理");
    }
    public void getAddressList(){
        model.getAddressList()
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(AddressManagerViewModel.this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("请求中。。。");
                    }
                }).subscribe(new DisposableObserver<BaseResponse<AddressListEntity>>() {

            @Override
            public void onNext(BaseResponse<AddressListEntity> addressListEntity) {
                list.clear();
                if(addressListEntity.getResult()!=null && addressListEntity.getResult().getAddress()!=null){
                    for (AddressEntity address : addressListEntity.getResult().getAddress()) {
                        AddressItemViewModel item  = new AddressItemViewModel(AddressManagerViewModel.this,address);
                        list.add(item);
                    }
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
    public void onResume() {
        KLog.e("sss  ");
        getAddressList();
    }

    //新建地址
    public BindingCommand createAddressClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            toAddUpdate(null);
        }
    });
    //跳转编辑页面
    public void toAddUpdate(AddressEntity addressEntity){
        Bundle bundle = new Bundle();
        bundle.putString(AddressAddUpdateActivity.ADDREDDJSON, GsonUtil.GsonToString(addressEntity));
        startActivity(AddressAddUpdateActivity.class,bundle);
    }
}
