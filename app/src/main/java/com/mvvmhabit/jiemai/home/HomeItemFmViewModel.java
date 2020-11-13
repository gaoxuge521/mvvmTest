package com.mvvmhabit.jiemai.home;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.example.swipetestdemo.R;
import com.mvvmhabit.entity.FlashCartEntity;
import com.mvvmhabit.entity.HomeEntity;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HomeItemFmViewModel extends MultiItemViewModel<HomeFmViewModel> {
    public ObservableField<HomeEntity.Product> homeEntityObservableField = new ObservableField<>();
    public ObservableField<Integer> onTextChange = new ObservableField<>(0);
    public ObservableField<Integer> animButtonChange = new ObservableField<>(View.VISIBLE);
    public Drawable drawable;
    public HomeItemFmViewModel(@NonNull HomeFmViewModel viewModel, HomeEntity.Product entity) {
        super(viewModel);
        homeEntityObservableField.set(entity);
        drawable = ContextCompat.getDrawable(viewModel.getApplication(), R.drawable.img_default);
        setAnimButtonVisible();
    }

    public void setAnimButtonVisible(){
        animButtonChange.set(homeEntityObservableField.get().getQty()==0?View.GONE:View.VISIBLE);
    }
    //刷新购物车数量
    public void setItemNums(FlashCartEntity flashCartEntity){
        if(flashCartEntity!=null && flashCartEntity.getItems()!=null){
            if(flashCartEntity.getItems().containsKey(homeEntityObservableField.get().getProduct_id())){
                FlashCartEntity.CartItem cartItem = flashCartEntity.getItems().get(homeEntityObservableField.get().getProduct_id());
                homeEntityObservableField.get().setCount(cartItem.getQty());
                homeEntityObservableField.get().setItemId(cartItem.getItemId());
                setOnTextChange(Integer.valueOf(cartItem.getQty()));
            }else{
                homeEntityObservableField.get().setCount("");
                homeEntityObservableField.get().setItemId("");
                setOnTextChange(0);
            }
        }else{
            homeEntityObservableField.get().setCount("");
            homeEntityObservableField.get().setItemId("");
            setOnTextChange(0);
        }
    }
    //设置数量
    public void setOnTextChange(int onTextChange) {
        this.onTextChange.set(onTextChange);
    }


    //点击
    public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.toGoodDetail(homeEntityObservableField.get().getProduct_id());
        }
    });
    //长按
    public BindingCommand onLongItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
        }
    });

    //
    public BindingCommand onAddClickCommend = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KLog.e("sss   "+viewModel.isLogin());
            if(viewModel.isLogin()){
                viewModel.goodAdd(homeEntityObservableField.get().getProduct_id(),"1");
            }else{
                viewModel.doLogin();
            }

        }
    });
    //
    public BindingCommand onDeleteClickCommend = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(viewModel.isLogin()){
                //opType（0-删除 1-减 2-加）
                if(homeEntityObservableField.get().getCount().equals("1")){
                    viewModel.goodDel(homeEntityObservableField.get().getItemId(),"0");
                }else{
                    viewModel.goodDel(homeEntityObservableField.get().getItemId(),"1");
                }

            }else{
                viewModel.doLogin();
            }

        }
    });
    public BindingCommand onTextClickCommend = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.cartNumEvent.setValue(homeEntityObservableField.get().getItemId());
        }
    });

}
