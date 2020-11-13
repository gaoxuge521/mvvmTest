package com.mvvmhabit.jiemai.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.FragmentMeBinding;
import com.mvvmhabit.app.AppViewModelFactory;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MeFragment extends BaseFragment<FragmentMeBinding,MeFmViewModel> {

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_me;
    }

    @Override
    public MeFmViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MeFmViewModel.class);
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public void initData() {
        getUserInfo();
    }

    @Override
    public void initViewObservable() {
        viewModel.out.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //删除选择对话框
                MaterialDialogUtils.showBasicDialog(getContext(), "提示", "是否退出登录")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ToastUtils.showShort("取消");
                            }
                        }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        viewModel.logout();
                    }
                }).show();
            }
        });
        viewModel.outAvatar.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                KLog.e("sss   设置头像");
                binding.ivAvatar.setImageResource(R.mipmap.logo);
            }
        });
    }

    public void getUserInfo(){
        viewModel.getUserInfo();
    }
}
