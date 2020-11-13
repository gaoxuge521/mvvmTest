package com.mvvmhabit.jiemai.order;


import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.example.swipetestdemo.R;
import com.mvvmhabit.entity.OrderGenerationEntity;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.ItemViewModel;

public class OrderGenerationProductItemViewModel extends ItemViewModel<OrderGenerationViewModel> {

    public ObservableField<OrderGenerationEntity.NoProductsManjian> entity = new ObservableField<>();
    public Drawable drawable;
    public OrderGenerationProductItemViewModel(@NonNull OrderGenerationViewModel viewModel, OrderGenerationEntity.NoProductsManjian productsManjian) {
        super(viewModel);
        drawable = ContextCompat.getDrawable(viewModel.getApplication(), R.drawable.img_default);
        entity.set(productsManjian);
    }
}
