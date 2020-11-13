package com.mvvmhabit.utils;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import me.goldze.mvvmhabit.utils.Utils;

public class BaseUtils  {

    //////////////////////////////////////////////////
    //adapters

    /**
     * Helper to throw an exception when {@link (int,
     * Object)} returns false.
     */
    public static void throwMissingVariable(ViewDataBinding binding, int bindingVariable, @LayoutRes int layoutRes) {
        Context context = binding.getRoot().getContext();
        Resources resources = context.getResources();
        String layoutName = resources.getResourceName(layoutRes);
        String bindingVariableName = DataBindingUtil.convertBrIdToString(bindingVariable);
        throw new IllegalStateException("Could not bind variable '" + bindingVariableName + "' in layout '" + layoutName + "'");
    }
}
