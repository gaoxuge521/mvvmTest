package com.mvvmhabit.jiemai.home;

import android.app.Application;
import android.os.Bundle;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.example.swipetestdemo.R;
import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.ActivityEntity;
import com.mvvmhabit.entity.FlashCartEntity;
import com.mvvmhabit.entity.GoodNumChangeEntity;
import com.mvvmhabit.entity.HomeEntity;
import com.mvvmhabit.entity.HomeHeadEntity;
import com.mvvmhabit.entity.NoticeEntity;
import com.mvvmhabit.jiemai.good.GoodDetailActivity;
import com.mvvmhabit.jiemai.login.LoginJieMaiActivity;
import com.mvvmhabit.ui.home.HomeItemViewModel;
import com.mvvmhabit.utils.Contans;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
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
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class HomeFmViewModel extends BaseViewModel<DemoRepository> {
    private HomeEntity homeEntity;
    private static final String MultiRecycleType_Head = "head";
    private static final String MultiRecycleType_FOOT = "foot";
    private static final String MultiRecycleType_ITEM = "item";
    //是否加载更多
    public ObservableField<Boolean> refreshing = new ObservableField<>(false);
    //列表商品数量修改
    public SingleLiveEvent<String> cartNumEvent = new SingleLiveEvent<String>();
    private int page = 0;
    public UIchangeObservable ui = new UIchangeObservable();
    public class UIchangeObservable{
        public SingleLiveEvent refresh =  new SingleLiveEvent();
        public SingleLiveEvent loadMore =  new SingleLiveEvent();
    }
    //列表数据
    public ObservableList<MultiItemViewModel> list = new ObservableArrayList<>();
    public ItemBinding<MultiItemViewModel> itemBinding = ItemBinding.of(new OnItemBind<MultiItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, MultiItemViewModel item) {
            String itemType = (String) item.getItemType();
            if(itemType.equals(MultiRecycleType_Head)){
                itemBinding.set(com.example.swipetestdemo.BR.viewModel, R.layout.head_home_fm_view);
            }else  if(itemType.equals(MultiRecycleType_ITEM)){
                itemBinding.set(com.example.swipetestdemo.BR.viewModel,R.layout.item_home_fm);
            }else  if(itemType.equals(MultiRecycleType_FOOT)){
                itemBinding.set(com.example.swipetestdemo.BR.viewModel,R.layout.item_foot_load_more_view);
            }
        }
    });
    public HomeFmViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);

        KLog.e("sss   ");
        addHead();
    }

    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            page = 0;
            getHome(page);
        }
    });
    //加载更多
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            page ++;
            homeLoadMore(page);
        }
    });



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
                            if(homeHeadEntityBaseResponse.getResult()!=null && homeHeadEntityBaseResponse.getResult().getItems_count()>0){
                                //刷新购物车
                                Messenger.getDefault().send("", Contans.FLASHCART);
                            }
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
                            if(homeHeadEntityBaseResponse.getResult()!=null && homeHeadEntityBaseResponse.getResult().getItems_count()>0){
                                //刷新购物车
                                Messenger.getDefault().send("",Contans.FLASHCART);
                            }
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
                            if(homeHeadEntityBaseResponse.getResult()!=null && homeHeadEntityBaseResponse.getResult().getItems_count()>0){
                                //刷新购物车
                                Messenger.getDefault().send("",Contans.FLASHCART);
                            }
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

    //刷新购物车
    public void updateCart(){
        ui.refresh.call();
        //刷新购物车
        Messenger.getDefault().send("",Contans.FLASHCART);
    }

    public void addHead(){  //添加头部
        list.clear();
        MultiItemViewModel headViewModel = new HomeHeadFmViewModel(HomeFmViewModel.this);
        headViewModel.multiItemType(MultiRecycleType_Head);
        list.add(headViewModel);}
    //获取首页列表数据
    public void getHome(final int page){

        model.getHome(page)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .doOnSubscribe(this).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                showDialog();
            }
        }).doOnNext(new Consumer<BaseResponse<HomeEntity>>() {
            @Override
            public void accept(BaseResponse<HomeEntity> homeEntityBaseResponse) throws Exception {

                homeEntity = homeEntityBaseResponse.getResult();

            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                KLog.e("sss  失败" + throwable.getMessage());
            }
        }).observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponse<HomeEntity>, Observable<BaseResponse<HomeHeadEntity>>>() {

                    @Override
                    public Observable<BaseResponse<HomeHeadEntity>> apply(BaseResponse<HomeEntity> o) throws Exception {
                        KLog.e("sss  getHomeHeadData");
                        return model.getHomeHeadData();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<BaseResponse<HomeHeadEntity>>() {
                    @Override
                    public void accept(BaseResponse<HomeHeadEntity> homeHeadEntityBaseResponse) throws Exception {
                        if(list.size()>0){
                            HomeHeadFmViewModel multiItemViewModel = (HomeHeadFmViewModel) list.get(0);
                            multiItemViewModel.setEntityObservableField(homeHeadEntityBaseResponse.getResult());
                        }
                    }

                }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                KLog.e("sss  失败" + throwable.getMessage());
            }
        }).observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponse<HomeHeadEntity>, Observable<BaseResponse<NoticeEntity>>>() {
                    @Override
                    public Observable<BaseResponse<NoticeEntity>> apply(BaseResponse<HomeHeadEntity> homeHeadEntity) throws Exception {
                        KLog.e("sss  getNotice");
                        return model.getNotice();
                    }

                }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<BaseResponse<NoticeEntity>>() {
                    @Override
                    public void accept(BaseResponse<NoticeEntity> noticeEntityBaseResponse) throws Exception {
                        if(list.size()>0){
                            HomeHeadFmViewModel multiItemViewModel = (HomeHeadFmViewModel) list.get(0);
                            multiItemViewModel.setNoticeList(noticeEntityBaseResponse.getResult());
                        }

                    }
                }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                KLog.e("sss   getNoticeFaild" + throwable.getMessage());
            }
        }).observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponse<NoticeEntity>, Observable<BaseResponse<ActivityEntity>>>() {
                    @Override
                    public Observable<BaseResponse<ActivityEntity>> apply(BaseResponse<NoticeEntity> noticeEntity) throws Exception {
                        return model.getHomeActivity();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseResponse<ActivityEntity>>() {


                    @Override
                    public void onNext(BaseResponse<ActivityEntity> activityEntityBaseResponse) {
                        KLog.e("sss   getHomeActivity Success");
                        //设置数据
                        setHomeData(homeEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("sss   getHomeActivity faild" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }

    //上拉加载
    public void homeLoadMore(final int page){
        model.getHome(page)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                }).subscribe(new DisposableObserver<BaseResponse<HomeEntity>>() {

            @Override
            public void onNext(BaseResponse<HomeEntity> homeEntityBaseResponse) {
                dismissDialog();
                setHomeData(homeEntityBaseResponse.getResult());
            }

            @Override
            public void onError(Throwable e) {
                dismissDialog();

            }

            @Override
            public void onComplete() {
                dismissDialog();
                ui.loadMore.call();
                //刷新购物车
                Messenger.getDefault().send("",Contans.FLASHCART);

            }
        });
    }

    public void setHomeData(HomeEntity homeEntityBaseResponse){
        if(homeEntityBaseResponse.getProduct().size()==0){
            refreshing.set(false);
            MultiItemViewModel itemViewModel = new HomeFootViewModel(HomeFmViewModel.this);
            itemViewModel.multiItemType(MultiRecycleType_FOOT);
            list.add(itemViewModel);
        }else{
            refreshing.set(true);
            for (HomeEntity.Product product : homeEntityBaseResponse.getProduct()) {
                MultiItemViewModel itemViewModel = new HomeItemFmViewModel(HomeFmViewModel.this,product);
                itemViewModel.multiItemType(MultiRecycleType_ITEM);
                list.add(itemViewModel);
            }
        }
        updateCart();
    }
    @Override
    public void onDestroy() {
        if(list!=null && list.size()>0){
            try {
                HomeHeadFmViewModel multiItemViewModel = (HomeHeadFmViewModel) list.get(0);
                multiItemViewModel.stopBanner();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.onDestroy();
    }

    //登录
    public void doLogin(){
        startActivity(LoginJieMaiActivity.class);
    }
    public BindingCommand serachClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("搜索");
        }
    });
    public int getItemPosition(HomeItemViewModel viewModel){
        return list.indexOf(viewModel);
    }

    public void toGoodDetail(String productid){
        Bundle bundle = new Bundle();
        bundle.putString(GoodDetailActivity.PRODUCTID,productid);
        startActivity(GoodDetailActivity.class,bundle);
    }

    //刷新购物车数量
    public void notifyCartNums(FlashCartEntity response){
        for (MultiItemViewModel multiItemViewModel : list) {
            if(multiItemViewModel.getItemType().equals(MultiRecycleType_ITEM)){
                HomeItemFmViewModel homeItemFmViewModel = (HomeItemFmViewModel) multiItemViewModel;
                homeItemFmViewModel.setItemNums(response);
            }
        }
    }

}
