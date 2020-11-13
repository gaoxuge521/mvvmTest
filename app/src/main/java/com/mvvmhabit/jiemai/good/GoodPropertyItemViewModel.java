package com.mvvmhabit.jiemai.good;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.goldze.mvvmhabit.entity.GoodDetailEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class GoodPropertyItemViewModel extends ItemViewModel<GoodDetailViewModel> {
    public ObservableField<GoodDetailEntity.Groupattrarr> group = new ObservableField<>();
    public GoodPropertyItemViewModel(@NonNull GoodDetailViewModel viewModel,GoodDetailEntity.Groupattrarr groupattrarr) {
        super(viewModel);
        group.set(groupattrarr);
    }
}
