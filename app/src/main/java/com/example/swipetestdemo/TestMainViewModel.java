package com.example.swipetestdemo;

import android.app.Application;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class TestMainViewModel extends BaseViewModel {
    public TestMainViewModel(Application application) {
        super(application);
    }

    public BindingCommand cehuaClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });
}
