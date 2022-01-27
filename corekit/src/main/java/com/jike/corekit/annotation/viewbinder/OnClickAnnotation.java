package com.jike.corekit.annotation.viewbinder;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jike.corekit.BaseFragment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class OnClickAnnotation {

    public static void deploy(AppCompatActivity activity){
        try {
            Class clz = activity.getClass();
            Method[] methods = clz.getDeclaredMethods();
            for(Method method : methods){
                OnClick onClick = method.getAnnotation(OnClick.class);
                if (onClick != null) {
                    int viewId = onClick.id();
                    Method findView = clz.getMethod("findViewById", int.class);
                    View view = (View) findView.invoke(activity, viewId);
                    if (view != null) {
                        method.setAccessible(true);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Type[] types = method.getGenericParameterTypes();
                                    //判断方法参数长度
                                    if(types.length == 0){
                                        method.invoke(activity);
                                    }else {
                                        method.invoke(activity, view);
                                    }
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
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

            Method[] methods = clz.getDeclaredMethods();
            for(Method method : methods){
                if (method.isAnnotationPresent(OnClick.class)) {
                    OnClick onClick = method.getAnnotation(OnClick.class);
                    int viewId = onClick.id();
                    Method findView = clz.getMethod("findViewById", int.class);
                    View view = (View) findView.invoke(fragment, viewId);
                    if (view != null) {
                        method.setAccessible(true);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Type[] types = method.getGenericParameterTypes();
                                    //判断方法参数长度
                                    if(types.length == 0){
                                        method.invoke(fragment);
                                    }else {
                                        method.invoke(fragment, view);
                                    }
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                }
            }

        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
