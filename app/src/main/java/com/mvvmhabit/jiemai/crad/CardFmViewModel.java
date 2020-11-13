package com.mvvmhabit.jiemai.crad;

import android.app.Application;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;


import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;


import com.example.swipetestdemo.R;
import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.CartInfoEntity;
import com.mvvmhabit.entity.FlashCartEntity;
import com.mvvmhabit.entity.GoodNumChangeEntity;
import com.mvvmhabit.entity.OrderGenerationEntity;
import com.mvvmhabit.jiemai.MainActivity;
import com.mvvmhabit.jiemai.good.GoodDetailActivity;
import com.mvvmhabit.jiemai.login.LoginJieMaiActivity;
import com.mvvmhabit.jiemai.order.OrderGenerationActivity;
import com.mvvmhabit.ui.base.viewmodel.ToolbarViewModel;
import com.mvvmhabit.utils.Contans;
import com.mvvmhabit.utils.GsonUtil;

import java.util.HashMap;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class CardFmViewModel extends ToolbarViewModel<DemoRepository> {
    //购物车数据
    public ObservableField<CartInfoEntity> observableField = new ObservableField<>();
    //购物车列表
    public ItemBinding<CartInfoItemViewModel> itemBinding = ItemBinding.of(com.example.swipetestdemo.BR.viewModel, R.layout.item_cart_info);
    public ObservableList<CartInfoItemViewModel> list = new ObservableArrayList<>();
    //控制全选和取消全选按钮
    public SingleLiveEvent<Integer> onTextChange = new SingleLiveEvent<Integer>();
    //控制顶部编辑完成和底部结算和删除显示
    public SingleLiveEvent<Boolean> editDelChange = new SingleLiveEvent<>();
    //没有商品时显示去挑选商品视图
    public SingleLiveEvent<Boolean> hasGoods = new SingleLiveEvent<>();
    //购物车数量编辑弹框
    public SingleLiveEvent<String> cartNumEvent = new SingleLiveEvent<String>();
    //底部删除和结算
    public SingleLiveEvent<Integer> onSettlementTextChange = new SingleLiveEvent<Integer>();
    public CardFmViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {
        //初始化标题栏
        setRightTextVisible(View.VISIBLE);
        setLeftIconVisible(View.GONE);
        setRightText(getApplication().getString(R.string.edit));
        setTitleText(getApplication().getString(R.string.cart));
    }

    @Override
    protected void rightTextOnClick() {
        editDelChange.setValue(getRightText().equals(getApplication().getString(R.string.edit)));
    }
    public void toGoodDetail(String productid){
        Bundle bundle = new Bundle();
        bundle.putString(GoodDetailActivity.PRODUCTID,productid);
        startActivity(GoodDetailActivity.class,bundle);
    }
    //全选和取消全选
    public BindingCommand selectClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(observableField.get()!=null){
                if(isAllSelect(observableField.get())==0){
                    selectAll(1);
                }else{
                    selectAll(0);
                }
            }
        }
    });

    public BindingCommand settlementClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //结算
            if(getRightText().equals(getApplication().getString(R.string.edit))){
                if(observableField.get().getCartTotalNum()==0){
                    ToastUtils.showShort("请选择商品");
                }else{
                    initOrderByCart("");
                }

            }else{
                //删除
                if(observableField.get().getCartTotalNum()==0){
                    ToastUtils.showShort("请选择要删除的商品");
                }else{
                    String delIdStr = getDelIdStr();
                    KLog.e("sss  "+delIdStr);
                    goodDel(delIdStr,"0");
                }

            }
        }
    });
    //获取购物车数据
    public void getCartInfo(){
        if(!isLogin()){
            startActivity(LoginJieMaiActivity.class);
            Messenger.getDefault().send(MainActivity.HOME, Contans.SHOWHOME);
            return;
        }
        model.cartInfo()
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                }).subscribe(new DisposableObserver<BaseResponse<CartInfoEntity>>() {

            @Override
            public void onNext(BaseResponse<CartInfoEntity> cartInfoEntityBaseResponse) {
                dismissDialog();
                list.clear();
                if(cartInfoEntityBaseResponse.getResult()!=null){
                    observableField.set(cartInfoEntityBaseResponse.getResult());
                    //设置是否全选
                    onTextChange.setValue(isAllSelect(cartInfoEntityBaseResponse.getResult()));
                    onSettlementTextChange.setValue(cartInfoEntityBaseResponse.getResult().getCartTotalNum());
                    if(cartInfoEntityBaseResponse.getResult().getNoProductsManjian()!=null && cartInfoEntityBaseResponse.getResult().getNoProductsManjian().size()>0){
                        hasGoods.setValue(true);
                            for (CartInfoEntity.NoProductsManjian noProductsManjian : cartInfoEntityBaseResponse.getResult().getNoProductsManjian()) {
                                CartInfoItemViewModel itemViewModel = new CartInfoItemViewModel(CardFmViewModel.this,noProductsManjian);
                                list.add(itemViewModel);
                             }
                         }else{
                             hasGoods.setValue(false);
                         }
                    //刷新购物车
                    Messenger.getDefault().send("",Contans.FLASHCART);
                }

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

    /**
     *
     * @param entity
     * @return 选择有商品购买 0 没有全选 1 全选
     */
    public int isAllSelect(CartInfoEntity entity){
        if(entity==null || entity.getNoProductsManjian()==null){
            return 0;
        }
        int cartTotalNum = entity.getCartTotalNum();
        int allCount = 0;
        for (CartInfoEntity.NoProductsManjian noProductsManjian : entity.getNoProductsManjian()) {
            allCount+=noProductsManjian.getNum();
        }
        if(cartTotalNum<allCount){
            return 0;
        }else{
            return 1;
        }
    }
    //全选
    public void selectAll(int checked){
        model.selectAll(checked)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                }).subscribe(new DisposableObserver<BaseResponse<GoodNumChangeEntity>>() {

            @Override
            public void onNext(BaseResponse<GoodNumChangeEntity> cartInfoEntityBaseResponse) {
                dismissDialog();
                getCartInfo();
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("ssss   "+e.getMessage());
                dismissDialog();

            }

            @Override
            public void onComplete() {
                dismissDialog();

            }
        });
    }
    //全选
    public void selectOne(int checked,String id){
        model.selectOne(checked,id)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                }).subscribe(new DisposableObserver<BaseResponse<GoodNumChangeEntity>>() {

            @Override
            public void onNext(BaseResponse<GoodNumChangeEntity> cartInfoEntityBaseResponse) {
                dismissDialog();
                getCartInfo();

            }

            @Override
            public void onError(Throwable e) {
                KLog.e("ssss   "+e.getMessage());
                dismissDialog();

            }

            @Override
            public void onComplete() {
                dismissDialog();

            }
        });
    }

    //登录
    public void doLogin(){
        startActivity(LoginJieMaiActivity.class);
    }


    //增加购物车
    public void goodAdd(String id,String num){
        model.goodAdd(id,num)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("");
                    }
                })
                .subscribe(new DisposableObserver<BaseResponse<GoodNumChangeEntity>>() {
                    @Override
                    public void onNext(BaseResponse<GoodNumChangeEntity> homeHeadEntityBaseResponse) {
                        if(homeHeadEntityBaseResponse.isOk()){
                          getCartInfo();
                        }else{
                            ToastUtils.showShort(homeHeadEntityBaseResponse.getResult().getError());
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
    //修改购物车
    public void goodDel(String id,String num){
        model.updateInfo(id,num)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("");
                    }
                })
                .subscribe(new DisposableObserver<BaseResponse<GoodNumChangeEntity>>() {
                    @Override
                    public void onNext(BaseResponse<GoodNumChangeEntity> homeHeadEntityBaseResponse) {
                        if(homeHeadEntityBaseResponse.isOk()){
                            getCartInfo();
                        }else{
                            ToastUtils.showShort(homeHeadEntityBaseResponse.getResult().getError());
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

    public void initOrderByCart(String addressId){
        model.initOrderByCart(addressId)
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(CardFmViewModel.this)
                .subscribe(new DisposableObserver<BaseResponse<OrderGenerationEntity>>() {
                    @Override
                    public void onNext(BaseResponse<OrderGenerationEntity> response) {
                        Bundle bundle = new Bundle();
                        bundle.putString(OrderGenerationActivity.ORDER_JSON, GsonUtil.GsonToString(response.getResult()));
                        startActivity(OrderGenerationActivity.class,bundle);
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
    //修改购物车
    public void updateCart(String id,String num,String qty){
        model.updateInfo(id,num,qty)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("");
                    }
                })
                .subscribe(new DisposableObserver<BaseResponse<GoodNumChangeEntity>>() {
                    @Override
                    public void onNext(BaseResponse<GoodNumChangeEntity> homeHeadEntityBaseResponse) {
                        if(homeHeadEntityBaseResponse.isOk()){
                            getCartInfo();
                        }else{
                            ToastUtils.showShort(homeHeadEntityBaseResponse.getResult().getError());
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


    //刷新购物车数量
    public void notifyCartNums(FlashCartEntity response){
        for (CartInfoItemViewModel itemViewModel : list) {
            itemViewModel.setItemNums(response);
        }
    }
    //获取选择的id拼接字符串
    public String getDelIdStr(){
        StringBuffer stringBuffer = new StringBuffer();
        String ids = "";
        for (CartInfoEntity.NoProductsManjian noProductsManjian : observableField.get().getNoProductsManjian()) {
            if(noProductsManjian.getActive()==1){
                stringBuffer.append(noProductsManjian.getItemId()).append(",");
            }
        }
        if(stringBuffer.toString().length()>0){
            ids = stringBuffer.substring(0,stringBuffer.toString().length()-1);
        }
        return ids;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
