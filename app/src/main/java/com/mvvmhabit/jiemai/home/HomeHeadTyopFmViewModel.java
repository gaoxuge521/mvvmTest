package com.mvvmhabit.jiemai.home;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.example.swipetestdemo.R;
import com.mvvmhabit.entity.HomeHeadEntity;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HomeHeadTyopFmViewModel extends BaseViewModel {
    public ObservableField<HomeHeadEntity.CategoryAd> observableField = new ObservableField<>();
    public Drawable drawable;


    public HomeHeadTyopFmViewModel(@NonNull Application application, HomeHeadEntity.CategoryAd categoryAd) {
        super(application);
        drawable = ContextCompat.getDrawable(application, R.drawable.img_default);
        observableField.set(categoryAd);
    }

    public BindingCommand onClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort(observableField.get().getTitle());
        }
    });
}
