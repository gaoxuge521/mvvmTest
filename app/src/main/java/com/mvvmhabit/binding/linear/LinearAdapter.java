package com.mvvmhabit.binding.linear;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.databinding.BindingAdapter;

import com.example.swipetestdemo.R;
import com.mvvmhabit.entity.GoodDetailEntity;

import java.util.List;

public class LinearAdapter {

    @BindingAdapter(value = {"itemsData"},requireAll = false)
    public static void setItems(final LinearLayout layout, final List<GoodDetailEntity.Groupattrarr> list){
        if(list!=null){
            layout.removeAllViews();
            for (GoodDetailEntity.Groupattrarr groupattrarr : list) {
                View view = View.inflate(layout.getContext(), R.layout.item_good_proprety,null);
                TextView tv_key = view.findViewById(R.id.tv_key);
                TextView tv_value = view.findViewById(R.id.tv_value);
                tv_key.setText(groupattrarr.getKey());
                tv_value.setText(groupattrarr.getValue());
                layout.addView(view);
            }
        }
    }
}
