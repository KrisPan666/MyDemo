package com.hzy.mybook.utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hzy.mybook.R;

public class ToastUtils {

    private final static String TAG = "ToastUtils";

    // Toast
    private static Toast toast;
    private static Toast toastCenter;
    private static Toast toastResult;
    private static TextView textView;
    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        try{
            if (null == toast) {
                toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
            } else {
                toast.setText(message);
            }
            toast.show();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        try{
            if (null == toast) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                // toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(message);
            }
            toast.show();
        }catch(Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        try{
            if (null == toast) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                // toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(message);
            }
            toast.show();
        }catch(Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        try{
            if (null == toast) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                // toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(message);
            }
            toast.show();
        }catch(Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        try{
            if (null == toast) {
                toast = Toast.makeText(context, message, duration);
                // toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(message);
            }
            toast.show();
        }catch(Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        try{
            if (null == toast) {
                toast = Toast.makeText(context, message, duration);
                // toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(message);
            }
            toast.show();
        }catch(Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /** Hide the toast, if any. */
    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showCenterShort(Context context, CharSequence message) {
        try{
            if (null == toastCenter) {
                toastCenter = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
                toastCenter.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toastCenter.setText(message);
            }
            toastCenter.show();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void makeText(Context context, CharSequence text,
                                int duration) {
        if(null == toastResult){
            toastResult = new Toast(context);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.toast_hud, null);
            textView = (TextView) layout.findViewById(R.id.message);
            textView.setText(text);
            toastResult.setView(layout);
            toastResult.setDuration(duration);
        }else {
            textView.setText(text);
        }
        toastResult.show();
    }
    public static void toast(Context context, int resId) {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show();
    }

    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
