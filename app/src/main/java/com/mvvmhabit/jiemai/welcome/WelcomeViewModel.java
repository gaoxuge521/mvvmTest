package com.mvvmhabit.jiemai.welcome;

import android.app.Application;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import com.goldze.mvvmhabit.R;
import com.goldze.mvvmhabit.jiemai.MainActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class WelcomeViewModel extends BaseViewModel {
    public WelcomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void startTimer(){
        countDownTimer.start();
    }
    private CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
        public void onTick(long millisUntilFinished) {

        }

        public void onFinish() {
            countDownTimer.cancel();
            startActivity(MainActivity.class);
            finish();
        }
    };

    @Override
    public void onDestroy() {
        countDownTimer.cancel();
    }
}
