package com.wyk.framework.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileUtil {

    private static final long FILE_SIZE_KB = 1024;

    private static final long FILE_SIZE_MB = 1024 * 1024;

    private FileUtil() {
    }

    /**
     * 文件复制
     *
     * @param path  原路径
     * @param path1 目标路径
     */
    public static void IOCopy(String path, String path1) {
        File file = new File(path);
        File file1 = new File(path1);
        if (!file.exists()) {
            System.out.println(file.getName() + "文件不存在");
        } else {
            System.out.println("存在");
        }
        byte[] b = new byte[(int) file.length()];
        if (file.isFile()) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                fis = new FileInputStream(file);
                fos = new FileOutputStream(file1);
                fis.read(b);
                fos.write(b);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (file.isDirectory()) {
            if (!file.exists()) file.mkdir();
            if (!file1.exists()) file1.mkdir();
            String[] list = file.list();
            for (int i = 0; i < list.length; i++) {
                IOCopy(path + list[i], path1 + list[i]);
            }
        }
    }

    /**
     * 获得一个文件夹下的所有文件名称
     *
     * @param path
     * @return Collection<String>
     */
    public static Collection<String> getFilenames(String basepath, String path) {
        List<String> filesname = new ArrayList<String>();
        File file = new File(basepath + path);
        File[] lf = file.listFiles();
        if (lf != null) {
            for (int i = 0; i < lf.length; i++) {
                filesname.add(path + lf[i].getName());
            }
        }
        return filesname;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件的绝对路径
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }


    /**
     * 获取文件名（重名时，+1 处理）
     * @param baseFilePath
     * @param fileName
     * @param suffix
     * @return
     */
    public static String createOrRenameFile(String baseFilePath, String fileName, String suffix) {
        File originFile = new File(baseFilePath + fileName + suffix);
        if (!originFile.exists()) {
            return fileName + suffix;
        }

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String newFileName = String.format("%s(%d)%s", fileName, i, suffix);
            File newFile = new File(baseFilePath + newFileName);
            if(!newFile.exists()){
                return newFileName;
            }
        }

        return null;
    }

    /**
     * 获取文件的大小
     * @param file
     * @return
     */
    public static String getFileSize(MultipartFile file){
        String fileSize = "";
        long size = file.getSize();
        if(size > FILE_SIZE_MB){
            fileSize = (size / FILE_SIZE_MB) + "M";
        }else if(size > FILE_SIZE_KB && size < FILE_SIZE_MB){
            fileSize = (size / FILE_SIZE_KB) + "K";
        }else{
            fileSize = size + "B";
        }
        return fileSize;
    }

}

