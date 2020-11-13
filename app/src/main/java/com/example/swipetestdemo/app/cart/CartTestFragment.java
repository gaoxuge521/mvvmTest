package com.example.swipetestdemo.app.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swipetestdemo.R;
import com.example.swipetestdemo.databinding.FragmentCartTestBinding;
import com.example.swipetestdemo.widght.swiperv.SwipeRvAdapter;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import com.mvvmhabit.app.AppViewModelFactory;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;


public class CartTestFragment extends BaseFragment<FragmentCartTestBinding,CartTestViewModel> {

    private SwipeRvAdapter swipeRvAdapter;
    private RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;
    private RecyclerViewSwipeManager mRecyclerViewSwipeManager;
    private RecyclerView.Adapter wrappedAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_cart_test;
    }

    @Override
    public CartTestViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(CartTestViewModel.class);
    }

    @Override
    public int initVariableId() {
        return com.example.swipetestdemo.BR.viewModel;
    }

    @Override
    public void initData() {
//        setNavigatorBarStyle(NO_STATUS_BAR);
        viewModel.getCardInfo(true);
        ToastUtils.showShort("购物车");
        swipeRvAdapter = new SwipeRvAdapter();
        swipeRvAdapter.setItemBinding(viewModel.itemBinding);
        swipeRvAdapter.setItems(new ObservableArrayList());

        mRecyclerViewTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
        mRecyclerViewTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
        mRecyclerViewTouchActionGuardManager.setEnabled(true);
        // swipe manager
        mRecyclerViewSwipeManager = new RecyclerViewSwipeManager();

        // wrap for swiping
        wrappedAdapter = mRecyclerViewSwipeManager.createWrappedAdapter(swipeRvAdapter);
        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();

        // Change animations are enabled by default since support-v7-recyclerview v22.
        // Disable the change animation in order to make turning back animation of swiped item works properly.
        animator.setSupportsChangeAnimations(false);




        binding.rvCart.setLayoutManager( new LinearLayoutManager(requireContext()));
        binding.rvCart.setAdapter(wrappedAdapter);
        binding.rvCart.setItemAnimator(animator);

        mRecyclerViewTouchActionGuardManager.attachRecyclerView( binding.rvCart);
        mRecyclerViewSwipeManager.attachRecyclerView( binding.rvCart);


    }
    @Override
    public void onDestroyView() {
        if (mRecyclerViewSwipeManager != null) {
            mRecyclerViewSwipeManager.release();
            mRecyclerViewSwipeManager = null;
        }

        if (mRecyclerViewTouchActionGuardManager != null) {
            mRecyclerViewTouchActionGuardManager.release();
            mRecyclerViewTouchActionGuardManager = null;
        }

        if (binding.rvCart != null) {
            binding.rvCart.setItemAnimator(null);
            binding.rvCart.setAdapter(null);
        }

        if (wrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(wrappedAdapter);
            wrappedAdapter = null;
        }
        swipeRvAdapter = null;

        super.onDestroyView();
    }
    @Override
    public void initViewObservable() {
        viewModel.refreshList.observe(this, new Observer<ObservableList<ItemViewModel>>() {
            @Override
            public void onChanged(ObservableList<ItemViewModel> itemViewModels) {
                KLog.e("sss  "+itemViewModels.size());
                swipeRvAdapter.setItems(itemViewModels);
            }
        });
    }
}
