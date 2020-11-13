package com.mvvmhabit.ui.networklist;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.example.swipetestdemo.R;
import com.mvvmhabit.entity.DemoEntity;
import com.mvvmhabit.ui.network.detail.DetailFragment;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class NetWorkListItemViewModel extends ItemViewModel<NetWorkListViewModel> {
    public ObservableField<DemoEntity.ItemsEntity> entity = new ObservableField<>();
    public Drawable drawable;
    public NetWorkListItemViewModel(@NonNull NetWorkListViewModel viewModel, DemoEntity.ItemsEntity itemsEntity) {
        super(viewModel);
        this.entity .set(itemsEntity);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawable = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.ic_launcher);
    }

    public int getPosition(){
        return  viewModel.getPosition(this);
    }

    public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putParcelable("entity", entity.get());
            viewModel.startContainerActivity(DetailFragment.class.getCanonicalName(),bundle);
        }
    });

}
