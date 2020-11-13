package com.mvvmhabit.app;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mvvmhabit.data.DemoRepository;
import com.mvvmhabit.jiemai.MainViewModel;
import com.mvvmhabit.jiemai.address.AddressAddUpdateViewModel;
import com.mvvmhabit.jiemai.address.AddressManagerViewModel;
import com.mvvmhabit.jiemai.crad.CardFmViewModel;
import com.mvvmhabit.jiemai.good.GoodDetailViewModel;
import com.mvvmhabit.jiemai.home.HomeFmViewModel;
import com.mvvmhabit.jiemai.login.LoginJieMaiViewModel;
import com.mvvmhabit.jiemai.me.MeFmViewModel;
import com.mvvmhabit.jiemai.order.OrderGenerationViewModel;
import com.mvvmhabit.ui.home.HomeViewModel;
import com.mvvmhabit.ui.login.LoginViewModel;
import com.mvvmhabit.ui.network.NetWorkViewModel;
import com.mvvmhabit.ui.networklist.NetWorkListViewModel;

import io.reactivex.annotations.NonNull;

/**
 * Created by goldze on 2019/3/26.
 */
public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final DemoRepository mRepository;

    public static AppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppViewModelFactory(application, Injection.provideDemoRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private AppViewModelFactory(Application application, DemoRepository repository) {
        this.mApplication = application;
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NetWorkViewModel.class)) {
            return (T) new NetWorkViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(NetWorkListViewModel.class)) {
            return (T) new NetWorkListViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(HomeFmViewModel.class)) {
            return (T) new HomeFmViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(CardFmViewModel.class)) {
            return (T) new CardFmViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(MeFmViewModel.class)) {
            return (T) new MeFmViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(LoginJieMaiViewModel.class)) {
            return (T) new LoginJieMaiViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(GoodDetailViewModel.class)) {
            return (T) new GoodDetailViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(OrderGenerationViewModel.class)) {
            return (T) new OrderGenerationViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AddressManagerViewModel.class)) {
            return (T) new AddressManagerViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AddressAddUpdateViewModel.class)) {
            return (T) new AddressAddUpdateViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
