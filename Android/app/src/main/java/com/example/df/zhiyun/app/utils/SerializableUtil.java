package com.example.df.zhiyun.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableUtil {

    //把序列化的类写到文件里
    public static <T extends Serializable> void write(T t, File outFile) throws Exception {
        ObjectOutputStream oos = null;
        if(outFile == null){
            return;
        }
        try {
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }

            oos = new ObjectOutputStream(new FileOutputStream(outFile));
            oos.writeObject(t);
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }

    /**
     * 把文件转化成序列化的类
     * @param sourceFile
     * @return
     */
    public static Serializable read(File sourceFile) throws Exception {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(sourceFile));
            Object object = ois.readObject();

            if (object != null) {
                return (Serializable) object;
            }
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
        return null;
    }
}
