package com.cxz.hotfixlib;

import android.content.Context;

import com.cxz.hotfixlib.utils.ArrayUtils;
import com.cxz.hotfixlib.utils.Constants;
import com.cxz.hotfixlib.utils.ReflectUtils;

import java.io.File;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @author chenxz
 * @date 2019/3/17
 * @desc
 */
public class FixDexUtils {

    // 存放需要修复的dex集合
    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        // 修复之前，做清理工作
        loadedDex.clear();
    }

    public static void loadFixedDex(Context context) {
        if (context == null) return;
        // Dex 文件目录
        File fileDir = context.getDir(Constants.DEX_DIR, Context.MODE_PRIVATE);
        // 遍历私有目录中所有的文件
        File[] listFiles = fileDir.listFiles();
        for (File file : listFiles) {
            if (file.getName().endsWith(Constants.DEX_SUFFIX) && !"classes.dex".equals(file.getName())) {
                // 找到修复包dex文件，加入集合
                loadedDex.add(file);
            }
        }
        // 模拟类加载器
        createDexClassLoader(context, fileDir);
    }

    private static void createDexClassLoader(Context context, File fileDir) {
        // 临时的解压目录
        String optimizedDir = fileDir.getAbsolutePath() + File.separator + "opt_dex";
        // 创建改目录
        File optFile = new File(optimizedDir);
        if (!optFile.exists()) {
            optFile.mkdirs();
        }
        for (File dex : loadedDex) {
            // 初始化 DexClassLoader (自有)
            DexClassLoader classLoader = new DexClassLoader(dex.getAbsolutePath(),
                    optimizedDir, null, context.getClassLoader());
            // 每遍历一个需要修复的 dex 文件，就需要插队一次
            hotfix(classLoader, context);
        }
    }

    /**
     * 修复的核心代码
     *
     * @param classLoader 自有的 DexClassLoader
     * @param context     上下文对象
     */
    private static void hotfix(DexClassLoader classLoader, Context context) {
        // 需要获取系统的 PathClassLoader
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

        try {
            // 获取自有的 dexElements 数组
            Object myDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(classLoader));

            // 获取系统的 dexElements 数组
            Object systemDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(pathClassLoader));

            // 合并成新的 dexElements 数组，并设置自有的优先级
            Object dexElements = ArrayUtils.combineArray(myDexElements, systemDexElements);

            // 获取系统的 pathList 对象
            Object systemPathList = ReflectUtils.getPathList(pathClassLoader);

            // 重新赋值系统的 pathList 属性值 -- 修改的 dexElements 数组（新和成的）
            ReflectUtils.setField(systemPathList, systemPathList.getClass(), dexElements);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
