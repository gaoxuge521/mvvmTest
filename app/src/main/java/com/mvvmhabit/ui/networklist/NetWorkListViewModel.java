package com.mvvmhabit.ui.networklist;

import android.app.Application;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.example.swipetestdemo.R;
import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.entity.DemoEntity;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class NetWorkListViewModel extends BaseViewModel<DemoRepository> {

    private int page = 0;
    public UIChangeObservable ui = new UIChangeObservable();
    public class UIChangeObservable{
        public SingleLiveEvent finishRefresh = new SingleLiveEvent();
        public SingleLiveEvent finishLoadMore = new SingleLiveEvent();
    }
    public ObservableList<NetWorkListItemViewModel> observableList = new ObservableArrayList<>();
    public ItemBinding<NetWorkListItemViewModel> binding = ItemBinding.of(com.example.swipetestdemo.BR.viewModel, R.layout.item_network_list);
    public NetWorkListViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
    }

    //加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("加载数据");
            page++;
            getDataByNetWork();
        }
    });
    //刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("刷新数据");
            page=0;
            getDataByNetWork();
        }
    });

    //网络请求
    public void getDataByNetWork(){
        model.demoGet()
                .compose(RxUtils.schedulersTransformer())//线程调度,子线程到主线程
                .compose(RxUtils.exceptionTransformer())//// 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                showDialog("正在请求。。。");
            }
        }).subscribe(new DisposableObserver<BaseResponse<DemoEntity>>() {

            @Override
            public void onNext(BaseResponse<DemoEntity> data) {
                if(page==0){
                    observableList.clear();
                }
                if(data.getCode()==1){
                    for (DemoEntity.ItemsEntity item : data.getResult().getItems()) {
                        NetWorkListItemViewModel itemViewModel = new NetWorkListItemViewModel(NetWorkListViewModel.this,item);
                        observableList.add(itemViewModel);
                    }
                    if(page==0){
                       ui.finishRefresh.call();
                    }else{
                        ui.finishLoadMore.call();
                    }
                }else{
                    ToastUtils.showShort("请求失败！");
                }
            }

            @Override
            public void onError(Throwable e) {
                dismissDialog();
                if(page==0){
                    ui.finishRefresh.call();
                }else{
                    ui.finishLoadMore.call();
                }
                if(e instanceof ResponseThrowable){
                    ToastUtils.showShort(((ResponseThrowable) e).message);
                }
            }

            @Override
            public void onComplete() {
                dismissDialog();
                if(page==0){
                    ui.finishRefresh.call();
                }else{
                    ui.finishLoadMore.call();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public int getPosition(NetWorkListItemViewModel itemViewModel){
        return observableList.indexOf(itemViewModel);
    }
}
