package com.wyk.framework.operator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 动态加载类
 *
 * @author wyk
 */
public class ClassDynamicLoader extends ClassLoader {

    private static Logger logger = LogManager.getLogger(ClassDynamicLoader.class);

    private String classRootPath;

    /**
     * 用于指定动态类加载的前缀，如前缀不匹配，则调用父级类加载器
     */
    private String packageName;

    public ClassDynamicLoader() {

    }

    public ClassDynamicLoader(String classRootPath, String packageName) {
        this.classRootPath = classRootPath;
        this.packageName = packageName;
    }


    protected byte[] getBytes(String filename) throws IOException {
        File file = new File(filename);
        long len = file.length();
        byte raw[] = new byte[(int) len];
        FileInputStream fin = new FileInputStream(file);
        int r = fin.read(raw);
        fin.close();
        if (r != len) {
            throw new IOException("Can't read all, " + r + " != " + len);
        }
        return raw;
    }

    protected boolean compile(String javaFile) throws IOException {
        logger.info("编译文件:{}", javaFile);
        String command = "javac -classpath " + ClassDynamicLoader.class.getResource("/").getPath() + " -Xlint:unchecked " + javaFile;
        logger.info("执行的编译指令:{}", command);
        Process p = Runtime.getRuntime().exec(command);
        try {
            p.waitFor();
        } catch (InterruptedException ie) {
            logger.info(String.format("编译文件:%s 出错", javaFile), ie);
        }
        int ret = p.exitValue();
        return ret == 0;
    }

    public Class<? extends Object> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (!name.startsWith(packageName)) {
            return getParent().loadClass(name);
        }

        Class<? extends Object> clazz = null;

        if (classRootPath.endsWith("/")) {
            classRootPath = classRootPath.substring(0, classRootPath.length() - 1);
        }

        String classFileName = classRootPath + "/" + name.replace(".", "/") + ".class";
        File file = new File(classFileName);
        if (!file.exists()) {
            throw new RuntimeException("无法读取到类文件:" + classFileName);
        }
        logger.debug("类加载器:{} 加载字节码文件:{}", this, classFileName);
        try {
            byte raw[] = getBytes(classFileName);
            clazz = defineClass(name, raw, 0, raw.length);
        } catch (IOException ie) {
            logger.error(String.format("加载类:%s 出错", name), ie);
            throw new ClassNotFoundException(name + "加载出错!");
        }

        if (clazz == null) {
            clazz = findSystemClass(name);
        }
        if (resolve && clazz != null){
            resolveClass(clazz);
        }
        if (clazz == null) {
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }

    public String getClassRootPath() {
        return classRootPath;
    }

    public void setClassRootPath(String classRootPath) {
        this.classRootPath = classRootPath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
