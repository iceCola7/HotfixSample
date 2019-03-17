package com.cxz.hotfix.sample;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.cxz.hotfixlib.FixDexUtils;
import com.cxz.hotfixlib.utils.Constants;
import com.cxz.hotfixlib.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void show(View view) {
        ParamsSort sort = new ParamsSort();
        sort.math(this);
    }

    public void fix(View view) {

        // 通过接口向服务器下载了修复好的 dex 文件，将文件放置到 SDCard （暂定 classes2.dex）
        File sourceFile = new File(Environment.getExternalStorageDirectory(), Constants.DEX_NAME);
        // 目标文件：私有目录
        File targetFile = new File(getDir(Constants.DEX_DIR, Context.MODE_PRIVATE).getAbsolutePath()
                + File.separator + Constants.DEX_NAME);
        // 如果存在的话，清理之前修复过的
        if (targetFile.exists()) {
            targetFile.delete();
            Toast.makeText(this, "删除了~", Toast.LENGTH_LONG).show();
        }

        // 复制
        try {
            FileUtils.copyFile(sourceFile,targetFile);
            Toast.makeText(this, "复制 Dex 完成~", Toast.LENGTH_LONG).show();
            // 开始修复 dex
            FixDexUtils.loadFixedDex(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
