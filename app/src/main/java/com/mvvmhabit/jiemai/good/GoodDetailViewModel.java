package com.mvvmhabit.jiemai.good;

import android.app.Application;
import android.view.View;


import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.example.swipetestdemo.R;
import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.FavoriteEntity;
import com.mvvmhabit.entity.GoodDetailEntity;
import com.mvvmhabit.entity.GoodNumChangeEntity;
import com.mvvmhabit.jiemai.MainActivity;
import com.mvvmhabit.jiemai.login.LoginJieMaiActivity;
import com.mvvmhabit.ui.base.viewmodel.ToolbarViewModel;
import com.mvvmhabit.utils.Contans;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class GoodDetailViewModel extends ToolbarViewModel<DemoRepository> {
    //banner数据
    public ObservableList<String> images = new ObservableArrayList<>();
    //banner停止
    public ObservableField<Boolean> isStop = new ObservableField<>(false);
    public ObservableField<GoodDetailEntity> entity = new ObservableField<>();
    //商品标签列表
    public ObservableField<List<GoodDetailEntity.Groupattrarr>> tipList = new ObservableField<>();
    //商品详情rv数据
    public ObservableList<GoodDetailSecondViewModel> secondViewModels = new ObservableArrayList<>();
    public ItemBinding<GoodDetailSecondViewModel> secondViewModelItemBinding = ItemBinding.of(com.example.swipetestdemo.BR.viewModel, R.layout.item_good_second);
    //收藏
    public SingleLiveEvent<Integer> favoriteEvent = new SingleLiveEvent<>();
    //添加商品按钮显示与隐藏
    public SingleLiveEvent<Integer> iconAddVisible = new SingleLiveEvent<>();
    //轮播图页数显示
    public ObservableField<String> bannerPage = new ObservableField<>("");
    //本商品的itemid
    public ObservableField<String> itemId = new ObservableField<>("");
    //购物车小红点
    public ObservableField<Integer> cartNum = new ObservableField<>(0);//购物车全部数量
    public ObservableField<Integer> onTextChange = new ObservableField<>(0);//购物车本商品数量
    //之间修改购物车数量
    public SingleLiveEvent<String> cartNumEvent = new SingleLiveEvent<String>();
    //商品详情显示第二页
    public BindingCommand onDragNext = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onPagegNext.call();
        }
    });
    //商品详情显示第一页
    public BindingCommand onDragFirst = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onPagegFirst.call();
        }
    });
    //商品详情显示第一页
    public SingleLiveEvent<Integer> onPagegFirst = new SingleLiveEvent<Integer> ();
    //商品详情显示第二页
    public SingleLiveEvent<Integer>  onPagegNext = new SingleLiveEvent<Integer> ();
    public GoodDetailViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
    }

    //设置添加按钮显示隐藏
    public void setIconAddVisible(int visible){
        iconAddVisible.setValue(visible);
    }
    public void initToobar(){
        //初始化标题栏
        setRightTextVisible(View.GONE);
        setRightIconVisible(View.VISIBLE);
        setTitleText(getApplication().getString(R.string.good));
    }
    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
    public void getGoodDetailData(String id) {
        //可以调用addSubscribe()添加Disposable，请求与View周期同步
        model.goodDetail(id)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在请求...");
                    }
                })
                .subscribe(new DisposableObserver<BaseResponse<GoodDetailEntity>>() {
                    @Override
                    public void onNext(BaseResponse<GoodDetailEntity> response) {
                        if(response.getResult()!=null){
                            images.clear();
                            images.addAll( response.getResult().getProduct().getThumbnails_image());
                            entity.set(response.getResult());
                            favoriteEvent.setValue(response.getResult().getFavoriteFlag());
                            if(response.getResult().getProduct()!=null && response.getResult().getProduct().getGroupAttrArr()!=null){
                                tipList.set(response.getResult().getProduct().getGroupAttrArr());
                            }
                            if(response.getResult().getProduct()!=null && response.getResult().getProduct().getThumbnails_image()!=null){
                                for (String s : response.getResult().getProduct().getThumbnails_image()) {
                                    GoodDetailSecondViewModel secondViewModel = new GoodDetailSecondViewModel(GoodDetailViewModel.this,s);
                                    secondViewModels.add(secondViewModel);
                                }
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {


                    }

                    @Override
                    public void onComplete() {
                        //关闭对话框
                        dismissDialog();

                    }
                });
    }
    //登录
    public void doLogin(){
        startActivity(LoginJieMaiActivity.class);
    }
    //
    public BindingCommand onAddClickCommend = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KLog.e("sss   "+isLogin());
            if(isLogin()){
                goodAdd(entity.get().getProduct().get_id(),"1");
            }else{
                doLogin();
            }

        }
    });

    public BindingCommand addGoodClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(isLogin()){
                goodAdd(entity.get().getProduct().get_id(),"1");
            }else{
                doLogin();
            }
        }
    });
    public BindingCommand goCartClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
            Messenger.getDefault().send(MainActivity.CART, Contans.SHOWHOME);
        }
    });
    //
    public BindingCommand onDeleteClickCommend = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(isLogin()){
                //opType（0-删除 1-减 2-加）
                if(onTextChange.get()==1){
                    goodDel(itemId.get(),"0");
                }else{
                    goodDel(itemId.get(),"1");
                }

            }else{
               doLogin();
            }

        }
    });
    public BindingCommand onTextClickCommend = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            cartNumEvent.setValue(itemId.get());
        }
    });

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
                            Messenger.getDefault().send("", Contans.FLASHCART);
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
                            Messenger.getDefault().send("",Contans.FLASHCART);
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
                            Messenger.getDefault().send("",Contans.FLASHCART);
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
    /**
     * 收藏和取消收藏
     */
    public void favorite(String id) {
        //可以调用addSubscribe()添加Disposable，请求与View周期同步
        model.favorite(id)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在请求...");
                    }
                })
                .subscribe(new DisposableObserver<BaseResponse<FavoriteEntity>>() {
                    @Override
                    public void onNext(BaseResponse<FavoriteEntity> response) {
                        favoriteEvent.setValue(response.getResult().getFlag());
                    }

                    @Override
                    public void onError(Throwable throwable) {


                    }

                    @Override
                    public void onComplete() {
                        //关闭对话框
                        dismissDialog();

                    }
                });
    }
    public void stopBanner(){
        isStop.set(true);
    }

    public BindingCommand onBannerItemClick = new BindingCommand(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer integer) {
            KLog.e("sss   "+integer);

        }
    });
    public BindingCommand OnBannerListenerClick = new BindingCommand(new BindingConsumer<Integer>() {

        @Override
        public void call(Integer integer) {
            bannerPage.set((integer+1)+"/"+images.size());
        }
    });
    public BindingCommand shareClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("分享");
        }
    });

    @Override
    protected void rightIconOnClick() {
        KLog.e("sss  111");
        if(isLogin()){
            favorite(entity.get().getProduct().get_id());
        }else{
            startActivity(LoginJieMaiActivity.class);
        }
    }

    @Override
    public void onDestroy() {
        stopBanner();
        super.onDestroy();
    }
}
