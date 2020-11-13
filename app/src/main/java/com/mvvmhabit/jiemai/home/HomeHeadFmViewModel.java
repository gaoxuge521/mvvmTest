package com.mvvmhabit.jiemai.home;



import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.example.swipetestdemo.R;
import com.mvvmhabit.entity.HomeHeadEntity;
import com.mvvmhabit.entity.NoticeEntity;

import java.util.List;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HomeHeadFmViewModel extends MultiItemViewModel<HomeFmViewModel> {
    public ObservableField<HomeHeadEntity> entityObservableField = new ObservableField<>();
    public ObservableField<Boolean> isStop = new ObservableField<>(false);
    public ObservableField<Integer> groupBuyVisible = new ObservableField<Integer>(View.INVISIBLE);
    public ObservableField<Integer> preSaleVisible = new ObservableField<Integer>(View.INVISIBLE);
    public ObservableField<Integer> noticeVisible = new ObservableField<Integer>(View.INVISIBLE);

    public Drawable drawable;
    public ObservableList<String> images = new ObservableArrayList<>();
    public ObservableList<HomeHeadTyopFmViewModel> list = new ObservableArrayList<>();
    public ItemBinding<HomeHeadTyopFmViewModel> itemBinding = ItemBinding.of(com.example.swipetestdemo.BR.viewModel, R.layout.item_home_head_type_fm);
    public void setNoticeList(NoticeEntity list) {
        this.noticeList.set(list);
        if(noticeList!=null && noticeList.get().getMessage().size()>0){
            noticeVisible.set(View.VISIBLE);
        }else{
            noticeVisible.set(View.GONE);
        }
    }
    public ObservableField<NoticeEntity> noticeList = new ObservableField<>();
    public void setEntityObservableField(HomeHeadEntity entityObservableField) {
        this.entityObservableField .set(entityObservableField);
        if(entityObservableField.getActivityGroupAd()==null || TextUtils.isEmpty(entityObservableField.getActivityGroupAd().getHome_page_image())){
            groupBuyVisible.set(View.GONE);
        }else{
            groupBuyVisible.set(View.VISIBLE);
        }
        if(entityObservableField.getActivityPreSaleAd()==null || TextUtils.isEmpty(entityObservableField.getActivityPreSaleAd().getHome_page_image())){
            preSaleVisible.set(View.GONE);
        }else{
            preSaleVisible.set(View.VISIBLE);
        }

        images.clear();
        for (HomeHeadEntity.HomeAd homeAd : entityObservableField.getHomeAd()) {
            images.add(homeAd.getImage());
        }
        list.clear();
        for (HomeHeadEntity.CategoryAd categoryAd : entityObservableField.getCategoryAd()) {
            HomeHeadTyopFmViewModel tyopViewModel = new HomeHeadTyopFmViewModel(viewModel.getApplication(),categoryAd);
            list.add(tyopViewModel);
        }
    }

    public HomeHeadFmViewModel(@NonNull HomeFmViewModel viewModel) {
        super(viewModel);
        drawable = ContextCompat.getDrawable(viewModel.getApplication(), R.drawable.img_default);
    }


    public BindingCommand tgClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("团购");
            isStop.set(!isStop.get());
        }
    });
    public BindingCommand ysClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("预售");
        }
    });

    public void stopBanner(){
        isStop.set(true);
    }
    public BindingCommand onBannerItemClick = new BindingCommand(new BindingConsumer<Integer>() {

        @Override
        public void call(Integer integer) {
            ToastUtils.showShort("点击了"+integer);
        }
    });

    public BindingCommand noticeOnItemClickCommand = new BindingCommand(new BindingConsumer<NoticeEntity.NoticeMsg>() {
        @Override
        public void call(NoticeEntity.NoticeMsg noticeEntity) {
            ToastUtils.showShort(noticeEntity.getTitle());
        }
    });

}
