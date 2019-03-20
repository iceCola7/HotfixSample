package com.cxz.hotfixlib.utils;

import java.lang.reflect.Field;

/**
 * @author chenxz
 * @date 2019/3/17
 * @desc
 */
public class ReflectUtils {

    /**
     * 通过反射获取某对象，并设置私有可访问
     *
     * @param obj   该属性所属类的对象
     * @param clazz 该属性所属的类
     * @param field 属性名
     * @return 该属性对象
     */
    private static Object getField(Object obj, Class<?> clazz, String field)
            throws NoSuchFieldException, IllegalAccessException {
        Field localField = clazz.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    /**
     * 给某属性赋值，并设置私有可访问
     *
     * @param obj   该属性所属类的对象
     * @param clazz 该属性所属的类
     * @param value 值
     */
    public static void setField(Object obj, Class<?> clazz, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field localField = clazz.getDeclaredField("dexElements");
        localField.setAccessible(true);
        localField.set(obj, value);
    }

    /**
     * 通过反射获取 BaseDexClassLoader 对象中的 PathList 对象
     *
     * @param baseDexClassLoader BaseDexClassLoader 对象
     */
    public static Object getPathList(Object baseDexClassLoader)
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    /**
     * 通过反射获取 BaseDexClassLoader 对象中的 PathList 对象，在获取 dexElements 对象
     *
     * @param paramObject PathList对象
     */
    public static Object getDexElements(Object paramObject)
            throws NoSuchFieldException, IllegalAccessException {
        return getField(paramObject, paramObject.getClass(), "dexElements");
    }

}
