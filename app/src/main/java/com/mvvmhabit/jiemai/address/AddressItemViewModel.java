package com.mvvmhabit.jiemai.address;


import androidx.databinding.ObservableField;

import com.mvvmhabit.entity.AddressEntity;
import com.mvvmhabit.utils.Contans;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;

public class AddressItemViewModel extends ItemViewModel<AddressManagerViewModel> {
    public ObservableField<AddressEntity> entity = new ObservableField<AddressEntity>();
    public AddressItemViewModel(@NonNull AddressManagerViewModel viewModel, AddressEntity addressEntity) {
        super(viewModel);
        entity.set(addressEntity);
    }

    public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.toAddUpdate(entity.get());
        }
    });
    public BindingCommand onItemSelectClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Messenger.getDefault().send(entity.get().getId(), Contans.ADDRESS_ID);
            viewModel.finish();
        }
    });
}
