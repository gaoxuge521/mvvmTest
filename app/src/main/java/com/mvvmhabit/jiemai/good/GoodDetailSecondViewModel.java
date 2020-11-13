package com.mvvmhabit.jiemai.good;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.goldze.mvvmhabit.R;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class GoodDetailSecondViewModel  extends ItemViewModel<GoodDetailViewModel> {
    public ObservableField<String> observableField = new ObservableField<>();
    public Drawable drawable;
    public GoodDetailSecondViewModel(@NonNull GoodDetailViewModel viewModel,String url) {
        super(viewModel);
        observableField.set(url);
        drawable = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.logo);
    }
}
