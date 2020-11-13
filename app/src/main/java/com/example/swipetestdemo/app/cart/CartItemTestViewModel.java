package com.example.swipetestdemo.app.cart;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.widght.swiperv.SwipeViewModel;
import com.mvvmhabit.entity.CartInfoEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class CartItemTestViewModel extends ItemViewModel<CartTestViewModel> implements SwipeViewModel {
    private boolean pinned = false;
    public Drawable drawable;
    private int index;
    private int type;
    public ObservableField<CartInfoEntity.CartProducts> observableField = new ObservableField<CartInfoEntity.CartProducts>();
    public CartItemTestViewModel(@NonNull CartTestViewModel viewModel, CartInfoEntity.CartProducts cartProducts,int index) {
        super(viewModel);
        observableField.set(cartProducts);
        drawable = ContextCompat.getDrawable(viewModel.getApplication(), R.drawable.img_default);
        this.index = index;
    }

    @Override
    public void setPinned(boolean pinned) {
        this.pinned =pinned;
    }

    @Override
    public boolean isPinned() {
        return pinned;
    }

    @Override
    public long getId() {
        return index;
    }

    @Override
    public void setSwipeType(int type) {
        this.type = type;
    }

    @Override
    public int getSwipeType() {
        return type;
    }
}
