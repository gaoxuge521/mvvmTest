package com.mvvmhabit.binding;


import androidx.databinding.BindingAdapter;

import com.mvvmhabit.widget.vertical.DragLayout;

import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class DragLayoutAdapter {

    @BindingAdapter(value = {"onDragFirst","onDragNext"},requireAll = false)
    public static void onDrawListener(final DragLayout dragLayout, final BindingCommand firstBinding, final BindingCommand nextBinding){
        dragLayout.setNextPageListener(new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                nextBinding.execute();
            }

            @Override
            public void onDragFirst() {
                firstBinding.execute();
            }
        });

    }

}
