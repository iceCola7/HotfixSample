package com.cxz.hotfix.sample;

import android.content.Context;
import android.widget.Toast;

/**
 * @author chenxz
 * @date 2019/3/17
 * @desc Bug 类
 */
public class ParamsSort {

    public void math(Context context) {
        int a = 10;
        int b = 0;
        Toast.makeText(context, "计算结果：" + (a / b), Toast.LENGTH_LONG).show();
    }

}
