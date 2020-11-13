package com.example.swipetestdemo.app.cart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.widght.swiperv.SwipeItemBinding;
import com.example.swipetestdemo.widght.swiperv.SwipeOnItenBind;
import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.BaseResponseEntity;
import com.mvvmhabit.entity.CartInfoEntity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;

public class CartTestViewModel extends BaseViewModel<DemoRepository> {
    public ObservableField<Boolean> showEmptyViewtest = new ObservableField<>(true);
    public ObservableField<String> rightTitleLabel = new ObservableField<>("编辑");
    public SingleLiveEvent<ObservableList<ItemViewModel>> refreshList = new SingleLiveEvent<>();
    public SwipeItemBinding<CartItemTestViewModel> itemBinding = SwipeItemBinding.of(new SwipeOnItenBind<CartItemTestViewModel>() {
    @Override
    public void onItenBind(SwipeItemBinding<CartItemTestViewModel> itemBinding, int pisition, CartItemTestViewModel item) {
        itemBinding.set(com.example.swipetestdemo.BR.viewModel, R.layout.cart_item_test_layout);
        }
    });
    public ObservableList<ItemViewModel> list = new ObservableArrayList<>();
    public CartTestViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
//        statusBarHeight.set(Utils.getStatusBarHeight(application));
    }
    public BindingCommand onRightTitleLabelClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (rightTitleLabel.get().equals("编辑")) {
                rightTitleLabel.set("完成");
            } else {
                rightTitleLabel.set("编辑");
            }
        }
    });
    public void getCardInfo(boolean resetView) {
        addSubscribe(model.cartInfo()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<BaseResponseEntity>() {
                    @Override
                    public void accept(BaseResponseEntity baseResponseEntity) {
                        dismissDialog();
                        if (baseResponseEntity.isOk()) {
//                            CartService.getInstance().getFlashCart();
                            initData((CartInfoEntity) baseResponseEntity.getData(), resetView);
                        }
                    }
                }));
    }

    private void initData(CartInfoEntity data, boolean resetView) {
//        list.clear();
//        int index= 0;
//        if(data.getNoProductsManjian()!=null && data.getNoProductsManjian().size()>0){
//            showEmptyViewtest.set(false);
//            for (CartInfoEntity.CartProducts cartProducts : data.getNoProductsManjian()) {
//                CartItemTestViewModel cartItemViewModel = new CartItemTestViewModel(CartTestViewModel.this, cartProducts,index);
//                index++;
//                list.add(cartItemViewModel);
//            }
//        }else{
//            showEmptyViewtest.set(true);
//        }
//        refreshList.setValue(list);
//        KLog.e("sss  "+list.size());

    }
}
