package com.example.reflexlibrary.util;

import android.util.Log;

import com.example.reflexlibrary.bean.Student;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflexUtil {
    private static final String className = "com.example.reflexlibrary.bean.Student";
    private static final String TAG = "ReflexUtil";

    public final static void ReflexFun() {
        //反射基本用法
        forNameReflexFun(className);
        //通过反射复制任意对象
        copyObject();
        //使用反射构建
        useReflexFun();
    }

    private static void forNameReflexFun(String className) {
        try {

            //获取类的对象
            Class<?> studentClass = Class.forName(className);
            //class父类对象 基本类型会报错 基本包装类型可以
            Log.d(TAG, ": " + studentClass.getGenericSuperclass());
            //将基础类的规范名称返回为由Java语言规范定义
            Log.d(TAG, ": " + studentClass.getCanonicalName());

            //直接使用
            Student student = (Student) studentClass.newInstance();
            student.setName("吕布");
            Log.d(TAG, "newInstance：  " + student.toString());

            //通过 无参 构造函数 实例化对象
            Student student2 = (Student) studentClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
            Log.d(TAG, "newInstance2：  " + student2.toString());

            //通过 有参 private私有 构造函数 实例化对象
            Student student3 = (Student) studentClass.getDeclaredConstructor(new Class[]{String.class, int.class}).newInstance(new Object[]{"貂蝉", 18});
            Log.d(TAG, "newInstance3：  " + student3.toString());

            //通过 有参 private私有 构造函数 实例化对象 + 调用私有方法
            Method setAge = studentClass.getDeclaredMethod("setAge", int.class);
            //调用private私有方法 必须 setAccessible(true)
            setAge.setAccessible(true);
            setAge.invoke(student3, 28);
            //调用private私有属性设置值  必须 setAccessible(true)
            Field name = studentClass.getDeclaredField("name");
            name.set(student3, "大乔");
            Log.d(TAG, "newInstance4：  " + student3.toString() + "  方法类型 0:默认   1:public   2:private " + setAge.getModifiers());

            //获取所有对象属性及方法
            Field[] declaredFields = studentClass.getDeclaredFields();
            Method[] declaredMethods = studentClass.getDeclaredMethods();

            for (Field declaredField : declaredFields) {
                Log.d(TAG, "declaredFields:  " + declaredField.getName());
            }
            for (Method declaredMethod : declaredMethods) {
                Log.d(TAG, "declaredMethods:  " + declaredMethod.getName());
            }


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static void copyObject() {
        Student student = new Student();
        try {
            Student copyStudent = (Student) copyObjectUt(student);
            Log.d(TAG, "copyObject: " + copyStudent.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Object copyObjectUt(Object originalObject) throws Exception {

        //Class<?> aClass = Class.forName(originalObject.getClass().getName()); //原class对象
        //原class对象
        Class<?> aClass = originalObject.getClass();
        //目标class对象
        Object targetObject = aClass.newInstance();
        for (Field declaredField : aClass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            //Field 属性  可以给某个对象set这个属性值   可获取get某个对象的这个属性值
            //设置目标对象的这属性值 设置为 原对象的这个属性值
            declaredField.set(targetObject, declaredField.get(originalObject));
        }
        return targetObject;
    }

    private static void useReflexFun() {
        try {
            Object array = Array.newInstance(Class.forName("java.lang.String"), 6);

            Array.set(array, 1, "a");
            Log.d(TAG, "useReflexFun: 自己构建一维数组 设置值后 取值 " + Array.get(array, 1));

            //创建二维数组 6行6列
            int[] dimens = {6, 6};
            Object arrayTow = Array.newInstance(int.class, dimens);

            //获取第2行
            Object arrayObj = Array.get(arrayTow, 1);
            //赋值第2行第3列
            Array.set(arrayObj, 2, 99);

            int[][] arrayTow1 = (int[][]) arrayTow;
            Log.d(TAG, "useReflexFun: 自己构建二维数组 设置值后 取值 " + arrayTow1[1][2]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
