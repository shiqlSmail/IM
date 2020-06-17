package com.im.getway.server.config;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Component
public class ImgUtil {

    /**
     * 读数据库，获取图片输入流
     *
     * @param
     * @return
     * @throws
     */
    public FileInputStream query_getPhotoImageBlob(String adress) {
        FileInputStream is = null;
        File filePic = new File(adress);
        try {
            is = new FileInputStream(filePic);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return is;

    }

    /*
     * 获取图片并显示在页面
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "queryPic")
    public void queryPic(@RequestParam(required = false) String adress, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (adress != null) {
            response.setContentType("image/jpeg");
            FileInputStream is = this.query_getPhotoImageBlob(adress);
            if (is != null) {
                int i = is.available(); // 得到文件大小  
                byte data[] = new byte[i];
                is.read(data); // 读数据  
                is.close();
                response.setContentType("image/jpeg"); // 设置返回的文件类型  
                OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象  
                toClient.write(data); // 输出数据  
                toClient.close();
            }
        }
    }
}
