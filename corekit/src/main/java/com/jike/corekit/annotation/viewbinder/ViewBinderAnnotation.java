package com.jike.corekit.annotation.viewbinder;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jike.corekit.BaseFragment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewBinderAnnotation {

    public static void deploy(AppCompatActivity activity){
        try {
            Class clz = activity.getClass();
            Field[] fields = clz.getDeclaredFields();

            for (int i = 0; i < fields.length; ++i) {
                Field field = fields[i];
                ViewBinder viewBinder = field.getAnnotation(ViewBinder.class);
                if (viewBinder != null) {
                    //获取注解
                    int viewId = viewBinder.id();
                    Method findView = clz.getMethod("findViewById", int.class);
                    View view = (View) findView.invoke(activity, viewId);
                    field.setAccessible(true);
                    //赋值给注解标注的对象
                    field.set(activity, view);
                }
            }
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void deploy(BaseFragment fragment){
        try {
            Class clz = fragment.getClass();
            Field[] fields = clz.getDeclaredFields();

            for (int i = 0; i < fields.length; ++i) {
                Field field = fields[i];
                ViewBinder viewBinder = field.getAnnotation(ViewBinder.class);
                if (viewBinder != null) {
                    //获取注解
                    int viewId = viewBinder.id();
                    Method findView = null;

                    findView = clz.getMethod("findViewById", int.class);
                    View view = (View) findView.invoke(fragment, viewId);
                    field.setAccessible(true);
                    //赋值给注解标注的对象
                    field.set(fragment, view);
                }
            }
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
