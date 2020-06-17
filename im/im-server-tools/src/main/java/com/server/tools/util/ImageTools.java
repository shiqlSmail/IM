package com.server.tools.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

/** 
 * 通过java获取图片的宽和高 
 * @author shiql 
 */  
public class ImageTools {  
        
    /** 
     * 获取图片宽度 
     * @param file  图片文件 
     * @return 宽度 
     */  
    public static int getImgWidth(File file) {  
        InputStream is = null;  
        BufferedImage src = null;  
        int ret = -1;  
        try {  
            is = new FileInputStream(file);  
            src = javax.imageio.ImageIO.read(is);  
            ret = src.getWidth(null); // 得到源图宽  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return ret;  
    }  
        
        
    /** 
     * 获取图片高度 
     * @param file  图片文件 
     * @return 高度 
     */  
    public static int getImgHeight(File file) {  
        InputStream is = null;  
        BufferedImage src = null;  
        int ret = -1;  
        try {  
            is = new FileInputStream(file);  
            src = javax.imageio.ImageIO.read(is);  
            ret = src.getHeight(null); // 得到源图高  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return ret;  
    }

    /**
     * 获取图片大小
     * @param file  图片文件
     * @return 大小
     */
    public static String getImgSize(File file) throws IOException {
        FileInputStream fis = null;
        FileChannel fileChannel = null;
        if(file.exists() && file.isFile()){
            fis = new FileInputStream(file);
            fileChannel = fis.getChannel();
        }
        System.out.println("文件"+file.getName()+"的大小为："+fileChannel.size()+"byte");
        return fileChannel.size() +"byte";
    }

}  
