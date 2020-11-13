package com.mvvmhabit.ui.home;

import android.app.Application;
import android.os.Bundle;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.example.swipetestdemo.R;
import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.HomeEntity;
import com.mvvmhabit.entity.HomeHeadEntity;
import com.mvvmhabit.jiemai.good.GoodDetailActivity;
import com.mvvmhabit.utils.Contans;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;


public class HomeViewModel extends BaseViewModel<DemoRepository> {
    private static final String MultiRecycleType_Head = "head";
    public ObservableField<Boolean> refreshing = new ObservableField<>(false);
    private static final String MultiRecycleType_ITEM = "item";
    private int page = 0;
    public UIchangeObservable ui = new UIchangeObservable();
    public class UIchangeObservable{
        public SingleLiveEvent refresh =  new SingleLiveEvent();
        public SingleLiveEvent loadMore =  new SingleLiveEvent();
    }

    public ObservableList<MultiItemViewModel> list = new ObservableArrayList<>();
    public ItemBinding<MultiItemViewModel> itemBinding = ItemBinding.of(new OnItemBind<MultiItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, MultiItemViewModel item) {
            String itemType = (String) item.getItemType();
            if(itemType.equals(MultiRecycleType_Head)){
                itemBinding.set(com.example.swipetestdemo.BR.viewModel, R.layout.head_home_view);
            }else  if(itemType.equals(MultiRecycleType_ITEM)){
                itemBinding.set(com.example.swipetestdemo.BR.viewModel,R.layout.item_home);
            }
        }
    });

    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            page = 0;
            getHome(page);
        }
    });
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            page ++;
            getHome(page);
        }
    });

    public HomeViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
    }


    public void getHomeHeadData(){
        model.getHomeHeadData()
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .subscribe(new DisposableObserver<BaseResponse<HomeHeadEntity>>() {
                    @Override
                    public void onNext(BaseResponse<HomeHeadEntity> homeHeadEntityBaseResponse) {
                        KLog.e("sss   "+homeHeadEntityBaseResponse.getMessage());
                        if(list.size()>0){
                            HomeHeadViewModel multiItemViewModel = (HomeHeadViewModel) list.get(0);
                            multiItemViewModel.setEntityObservableField(homeHeadEntityBaseResponse.getResult());
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
    public void getHome(final int page){
        model.getHome(page)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if(page==0){
                            showDialog("请求中。。。");
                        }
                    }
                }).subscribe(new DisposableObserver<BaseResponse<HomeEntity>>() {

            @Override
            public void onNext(BaseResponse<HomeEntity> homeEntityBaseResponse) {
                dismissDialog();
                if(page==0){
                    list.clear();
                    MultiItemViewModel headViewModel = new HomeHeadViewModel(HomeViewModel.this);
                    headViewModel.multiItemType(MultiRecycleType_Head);
                    list.add(headViewModel);
                    getHomeHeadData();
                }

                if(homeEntityBaseResponse.getResult().getProduct().size()==0){
                    refreshing.set(false);
                }else{
                    refreshing.set(true);
                    for (HomeEntity.Product product : homeEntityBaseResponse.getResult().getProduct()) {
                        MultiItemViewModel itemViewModel = new HomeItemViewModel(HomeViewModel.this,product);
                        itemViewModel.multiItemType(MultiRecycleType_ITEM);
                        list.add(itemViewModel);
                    }
                }

                if(page==0){
                    ui.refresh.call();
                }else{
                    ui.loadMore.call();
                }
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("ssss   "+e.getMessage());
                dismissDialog();
                if(page==0){
                    ui.refresh.call();
                }else{
                    ui.loadMore.call();
                }
            }

            @Override
            public void onComplete() {
                KLog.e("ssss   ");
                dismissDialog();
                if(page==0){
                    ui.refresh.call();
                }else{
                    ui.loadMore.call();
                }
            }
        });
    }

    public void toGoodDetail(String productid){
        Bundle bundle = new Bundle();
        bundle.putString(GoodDetailActivity.PRODUCTID,productid);
        startActivity(GoodDetailActivity.class,bundle);
    }
    @Override
    public void onDestroy() {
        if(list!=null && list.size()>0){
            try {
                HomeHeadViewModel multiItemViewModel = (HomeHeadViewModel) list.get(0);
                multiItemViewModel.stopBanner();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.onDestroy();
    }

    public BindingCommand serachClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            HomeItemViewModel multiItemViewModel = (HomeItemViewModel) list.get(1);
            Messenger.getDefault().send(multiItemViewModel.homeEntityObservableField.get().getName(), Contans.HOME_DEMO);
        }
    });
    public int getItemPosition(HomeItemViewModel viewModel){
        return list.indexOf(viewModel);
    }
}
