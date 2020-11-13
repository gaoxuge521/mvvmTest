package com.mvvmhabit.jiemai.crad;


import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;

import com.example.swipetestdemo.R;
import com.mvvmhabit.entity.CartInfoEntity;
import com.mvvmhabit.entity.FlashCartEntity;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class CartInfoItemViewModel extends ItemViewModel<CardFmViewModel> {
    public ObservableField<CartInfoEntity.NoProductsManjian> entityField = new ObservableField<>();
    public Drawable drawable;
    public ObservableField<Boolean> onCountAddSuccess = new ObservableField<>();
    public ObservableField<Boolean> onCountDelSuccess = new ObservableField<>();
    public ObservableField<Integer> onTextChange = new ObservableField<>(0);
    public CartInfoItemViewModel(@NonNull CardFmViewModel viewModel, CartInfoEntity.NoProductsManjian entity) {
        super(viewModel);
        entityField.set(entity);
        drawable = ContextCompat.getDrawable(viewModel.getApplication(), R.drawable.img_default);
    }
    //设置数量
    public void setOnTextChange(int onTextChange) {
        this.onTextChange.set(onTextChange);
    }
    //刷新购物车数量
    public void setItemNums(FlashCartEntity flashCartEntity){
        if(flashCartEntity!=null && flashCartEntity.getItems()!=null){
            if(flashCartEntity.getItems().containsKey(entityField.get().getProduct_id())){
                FlashCartEntity.CartItem cartItem = flashCartEntity.getItems().get(entityField.get().getProduct_id());
                setOnTextChange(Integer.valueOf(cartItem.getQty()));
            }else{
                setOnTextChange(0);
            }
        }else{
            setOnTextChange(0);
        }
    }

    public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.selectOne(entityField.get().getActive()==0?1:0,entityField.get().getItemId());
        }
    });
    public BindingCommand onitemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.toGoodDetail(entityField.get().getProduct_id());
        }
    });

    //
    public BindingCommand onAddClickCommend = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KLog.e("sss   "+viewModel.isLogin());
            if(viewModel.isLogin()){
                viewModel.goodAdd(entityField.get().getProduct_id(),"1");
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
                if(entityField.get().getNum()==1){
                    viewModel.goodDel(entityField.get().getItemId(),"0");
                }else{
                    viewModel.goodDel(entityField.get().getItemId(),"1");
                }

            }else{
                viewModel.doLogin();
            }

        }
    });
    public BindingCommand onTextClickCommend = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.cartNumEvent.setValue(entityField.get().getItemId());
        }
    });
}
