package com.example.swipetestdemo.widght.swiperv;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public interface SwipeBindingAdapter<T> {
    void setItemBinding(@NonNull SwipeItemBinding<T> itemBinding);

    @NonNull
    SwipeItemBinding<T> getItemBinding();

    void setItems(@Nullable List<T> items);

    T getAdapterItem(int position);

    @NonNull
    ViewDataBinding onCreateBinding(@NonNull LayoutInflater inflater, @LayoutRes int layoutRes, @NonNull ViewGroup viewGroup);

    void onBindBinding(@NonNull ViewDataBinding binding, int variableId, @LayoutRes int layoutRes, int position, T item);

}
