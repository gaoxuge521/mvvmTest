package com.mvvmhabit.ui.home;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.example.swipetestdemo.R;
import com.mvvmhabit.entity.HomeHeadEntity;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HomeHeadViewModel extends MultiItemViewModel<HomeViewModel> {
    public ObservableField<HomeHeadEntity> entityObservableField = new ObservableField<>();
    public ObservableField<Boolean> isStop = new ObservableField<>(false);
    public Drawable drawable;
    public ObservableList<String> images = new ObservableArrayList<>();
    public ObservableList<HomeHeadTyopViewModel> list = new ObservableArrayList<>();
    public ItemBinding<HomeHeadTyopViewModel> itemBinding = ItemBinding.of(com.example.swipetestdemo.BR.viewModel, R.layout.item_home_head_type);
    public void setEntityObservableField(HomeHeadEntity entityObservableField) {
        this.entityObservableField .set(entityObservableField);


        images.clear();
        for (HomeHeadEntity.HomeAd homeAd : entityObservableField.getHomeAd()) {
            images.add(homeAd.getImage());
        }

        list.clear();
        for (HomeHeadEntity.CategoryAd categoryAd : entityObservableField.getCategoryAd()) {
            HomeHeadTyopViewModel tyopViewModel = new HomeHeadTyopViewModel(viewModel.getApplication(),categoryAd);
            list.add(tyopViewModel);
        }
    }

    public HomeHeadViewModel(@NonNull HomeViewModel viewModel) {
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


}
