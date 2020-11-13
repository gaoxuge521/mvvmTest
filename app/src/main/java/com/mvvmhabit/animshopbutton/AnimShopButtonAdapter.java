package com.mvvmhabit.animshopbutton;


import androidx.databinding.BindingAdapter;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;

public class AnimShopButtonAdapter {

    @BindingAdapter(value = {"onAddClickCommend","onDeleteClickCommend","onTextClickCommend"},requireAll = false)
    public static void onAddDelClickCommend(final AnimShopButtonUi animShopButton, final BindingCommand onAddClickCommend,final BindingCommand onDeleteClickCommend,final BindingCommand onTextClickCommend){
        animShopButton.setOnAddDelTextClick(new AnimShopButtonUi.OnAddDelTextClick() {
            @Override
            public void onAddClick() {
                onAddClickCommend.execute();
            }

            @Override
            public void onDelClick() {
                onDeleteClickCommend.execute();
            }

            @Override
            public void onTextClick() {
                onTextClickCommend.execute();
            }
        });
    }

    @BindingAdapter(value = {"onCountAddSuccess"},requireAll = false)
    public static void onAddSuccess(final AnimShopButtonUi animShopButton, boolean success){
        animShopButton.OnAddNext();
    }
    @BindingAdapter(value = {"onCountDelSuccess"},requireAll = false)
    public static void onCountDelSuccess(final AnimShopButtonUi animShopButton, boolean success){
        animShopButton.onDelNext();
    }
    @BindingAdapter(value = {"onCount"},requireAll = false)
    public static void onCount(final AnimShopButtonUi animShopButton, int success){
        animShopButton.setCount(success);
        animShopButton.postInvalidate();
    }
}
