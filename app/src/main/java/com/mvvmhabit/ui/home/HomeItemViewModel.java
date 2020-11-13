package com.mvvmhabit.ui.home;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.example.swipetestdemo.R;
import com.mvvmhabit.entity.HomeEntity;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HomeItemViewModel extends MultiItemViewModel<HomeViewModel> {
    public ObservableField<HomeEntity.Product> homeEntityObservableField = new ObservableField<>();
    public ObservableField<Boolean> onCountAddSuccess = new ObservableField<>();
    public ObservableField<Boolean> onCountDelSuccess = new ObservableField<>();
    public Drawable drawable;
    public HomeItemViewModel(@NonNull HomeViewModel viewModel, HomeEntity.Product entity) {
        super(viewModel);
        homeEntityObservableField.set(entity);
        drawable = ContextCompat.getDrawable(viewModel.getApplication(), R.drawable.img_default);
    }

//    //加入购物车
//    public BindingCommand onAddClick = new BindingCommand(new BindingAction() {
//        @Override
//        public void call() {
//            ToastUtils.showShort(homeEntityObservableField.get().getName()+"加入购物车");
//        }
//    });
    //点击
    public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort(homeEntityObservableField.get().getName()+"点击");
            viewModel.toGoodDetail(homeEntityObservableField.get().getProduct_id());
        }
    });
    //长按
    public BindingCommand onLongItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort(homeEntityObservableField.get().getName()+"长按");
        }
    });

    //
    public BindingCommand onAddClickCommend = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("增加");
        }
    });
    //
    public BindingCommand onDeleteClickCommend = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("减少");
        }
    });

}
