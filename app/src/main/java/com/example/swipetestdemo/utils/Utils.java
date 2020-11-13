package com.example.swipetestdemo.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    private static final String TAG = "Adapters";

    public static String smsSign(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 11) {
            return "";
        }
        String subString = phoneNumber.substring(phoneNumber.length() - 1);
        int off = Integer.parseInt(subString) % 10;
        String finalString = "";
        for (int i = 3; i < phoneNumber.length(); i++) {
            int value = Integer.parseInt(String.valueOf(phoneNumber.charAt(i)));
            char charValue = phoneNumber.charAt(i);
            int charA = value % 2;
            int number_to_a;
            if (charA == 1) {
                number_to_a = off + 23;
            } else {
                number_to_a = off + 49;
            }

            char newIndex = (char) (charValue + number_to_a);
            finalString += newIndex;
        }
        try {
            finalString = MD5(MD5(phoneNumber + finalString));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return finalString;
    }

    public static String orderSign(String order) {
        String oriNumber = order;

        String finalString = "";
        order = order.replaceAll("[^0-9]", "");
        if (order.length() > 8) {
            order = order.substring(0,8);
        }
        String subString = order.substring(order.length() - 1);
        int off = Integer.parseInt(subString) % 10;
        for (int i = 0; i < order.length(); i++) {
            int value = Integer.parseInt(String.valueOf(order.charAt(i)));
            char charValue = order.charAt(i);
            int charA = value % 2;
            int number_to_a;
            if (charA == 1) {
                number_to_a = off + 23;
            } else {
                number_to_a = off + 49;
            }

            char newIndex = (char) (charValue + number_to_a);
            finalString += newIndex;
        }
        try {
            finalString = MD5(MD5(oriNumber + finalString));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return finalString;
    }


    public static String MD5(String value) throws NoSuchAlgorithmException {
        byte[] secretBytes = null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(value.getBytes());
        secretBytes = md.digest();
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public static boolean isValidPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                return false;
            }
            return true;
        }
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

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

    /**
     * Ensures the call was made on the main thread. This is enforced for all ObservableList change
     * operations.
     */
    public static void ensureChangeOnMainThread() {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new IllegalStateException("You must only modify the ObservableList on the main thread.");
        }
    }

    public static boolean hitTest(View v, int x, int y) {
        final int tx = (int) (v.getTranslationX() + 0.5f);
        final int ty = (int) (v.getTranslationY() + 0.5f);
        final int left = v.getLeft() + tx;
        final int right = v.getRight() + tx;
        final int top = v.getTop() + ty;
        final int bottom = v.getBottom() + ty;

        return (x >= left) && (x <= right) && (y >= top) && (y <= bottom);
    }
    ////////////////////////////////////////////////////////////

    public static int getStatusBarHeight(Application application) {
        int height = 0;
        int resourceId = application.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = application.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    public static float pxToDp(Application application, float px) {
        return px / ((float) application.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    public static int dpToPx(Context context, int dp) {
        if (dp <= 0) return dp;
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String encodePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 11) {
            return phoneNumber;
        }
        return phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static long data2Second(String data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        long time = 0;
        try {
            data = data.replaceAll(":","-");
            time = simpleDateFormat.parse(data).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String seconds2days(int seconds) {
        if (seconds == 0) {
            return "00:00:00";
        }

        int curSeconds = seconds;

        int mins = 0;
        int hours = 0;
        int days = 0;

        if (curSeconds >= 60) {
            mins = curSeconds / 60;
            curSeconds = curSeconds % 60;
        }
        if (mins >= 60) {
            hours = mins / 60;
            mins = mins % 60;
        }
        if (hours >= 24) {
            days = hours / 24;
            hours = hours % 24;
        }

        if (days > 0) {
            return String.format("%d天%d小时%d分%d秒", days, hours, mins, curSeconds);
        } else {
            return String.format("%d小时%d分%d秒", hours, mins, curSeconds);
        }

    }


    public static String seconds2daysNoneH(int seconds) {
        if (seconds == 0) {
            return "00:00:00";
        }

        int curSeconds = seconds;

        int mins = 0;
        int hours = 0;
        int days = 0;

        if (curSeconds >= 60) {
            mins = curSeconds / 60;
            curSeconds = curSeconds % 60;
        }
        if (mins >= 60) {
            hours = mins / 60;
            mins = mins % 60;
        }
        if (hours >= 24) {
            days = hours / 24;
            hours = hours % 24;
        }

        if (days > 0) {
            return String.format("%d天%02d:%02d:%02d", days, hours, mins, curSeconds);
        } else {
            return String.format("%02d:%02d:%02d", hours, mins, curSeconds);
        }

    }


    public static PackageInfo getPackageInfo() {

        Context context = me.goldze.mvvmhabit.utils.Utils.getContext();
        PackageInfo pi = null;

        try {
            PackageManager pm = me.goldze.mvvmhabit.utils.Utils.getContext().getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    public static String getAppMetaData(String key) {
        Context ctx = me.goldze.mvvmhabit.utils.Utils.getContext();
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }

        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }
}
