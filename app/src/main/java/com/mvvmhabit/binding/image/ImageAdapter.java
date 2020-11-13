package com.mvvmhabit.binding.image;

import android.widget.ImageView;


import androidx.databinding.BindingAdapter;

import com.example.swipetestdemo.R;

import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class ImageAdapter {
    @BindingAdapter(value = {"imgSelect"},requireAll = false)
    public static void setImageState(final ImageView imageView, int imgSelect){
        if(imgSelect==0){
            imageView.setImageResource(R.drawable.icon_select_nor);
        }else {
            imageView.setImageResource(R.drawable.icon_select_pre);
        }
    }
}
