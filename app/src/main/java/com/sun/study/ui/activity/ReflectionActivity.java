package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.sun.study.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/4/17.
 */
public class ReflectionActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_info)
    TextView tvInfo;

    private static int i = 1;
    public String s = "reflect";
    protected Object obj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "反射");
        getClassObj();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reflection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_getClassObj:
                getClassObj();
                return true;
            case R.id.item_getFieldsInfo:
                getFieldsInfo();
                return true;
            case R.id.item_getMethodsInfo:
                getMethodsInfo();
                return true;
            case R.id.item_modifyFieldValue:
                modifyFieldValue();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getClassObj() {
        Class<?> cls1 = ReflectionActivity.class;

        ReflectionActivity activity = new ReflectionActivity();
        Class<?> cls2 = activity.getClass();

        Class<?> cls3 = null;
        try {
            cls3 = Class.forName("com.sun.study.ui.activity.ReflectionActivity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("cls1: ").append(cls1).append("\n\n");
        sb.append("cls2: ").append(cls2).append("\n\n");
        sb.append("cls3: ").append(cls3);
        tvInfo.setText(sb.toString());
        toolbar.setSubtitle("三种方式获得类对象");
    }

    private void getFieldsInfo() {
        Class<ReflectionActivity> cls = ReflectionActivity.class;
        Field[] fields = cls.getDeclaredFields();
        if (fields == null) return;

        StringBuilder sb = new StringBuilder();
        for (Field field:fields) {
            sb.append(Modifier.toString(field.getModifiers())).append(" ");
            sb.append(field.getType().getSimpleName()).append(" ");
            sb.append(field.getName()).append(";");
            sb.append("\n\n");
        }

        tvInfo.setText(sb.toString());
        toolbar.setSubtitle("获得类的所有属性信息");
    }

    private void getMethodsInfo() {
        Class<ReflectionActivity> cls = ReflectionActivity.class;
        Method[] methods = cls.getDeclaredMethods();
        if (methods == null) return;

        StringBuilder sb = new StringBuilder();
        for (Method method:methods) {
            sb.append(Modifier.toString(method.getModifiers())).append(" ");
            sb.append(method.getReturnType()).append(" ");
            sb.append(method.getName()).append("(");
            Class[] parameters = method.getParameterTypes();
            if (parameters != null) {
                for (int i=0; i<parameters.length; i++) {
                    Class paramCls = parameters[i];
                    sb.append(paramCls.getSimpleName());
                    if (i < parameters.length - 1) sb.append(", ");
                }
            }
            sb.append(")\n\n");
        }

        tvInfo.setText(sb.toString());
        toolbar.setSubtitle("获得类的所有方法信息");
    }

    private void modifyFieldValue() {
        Class<ReflectionActivity> cls = ReflectionActivity.class;
        Field[] fields = cls.getDeclaredFields();
        if (fields == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("获得类的所有属性信息：\n\n");
        for (Field field:fields) {
            sb.append(Modifier.toString(field.getModifiers())).append(" ");
            sb.append(field.getType().getSimpleName()).append(" ");
            sb.append(field.getName()).append(";");
            sb.append("\n\n");
        }

        try {
            sb.append("属性i的默认值：i = ");
            Field f = cls.getDeclaredField("i");
            sb.append(f.getInt("i")).append("\n\n");
            f.set("i", 100);
            sb.append("属性i修改后的值：i = ");
            sb.append(f.getInt("i")).append("\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvInfo.setText(sb.toString());
        toolbar.setSubtitle("修改类型Int属性i的值");
    }
}
