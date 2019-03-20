package com.cxz.hotfixlib.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author chenxz
 * @date 2019/3/17
 * @desc
 */
public class FileUtils {

    /**
     * 复制文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {

        FileInputStream inputStream = new FileInputStream(sourceFile);
        BufferedInputStream inBuffer = new BufferedInputStream(inputStream);

        FileOutputStream outputStream = new FileOutputStream(targetFile);
        BufferedOutputStream outBuffer = new BufferedOutputStream(outputStream);

        // 缓存数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuffer.read(b)) != -1) {
            outBuffer.write(b, 0, len);
        }
        outBuffer.flush();

        inputStream.close();
        inBuffer.close();
        outputStream.close();
        outBuffer.close();

    }

}
