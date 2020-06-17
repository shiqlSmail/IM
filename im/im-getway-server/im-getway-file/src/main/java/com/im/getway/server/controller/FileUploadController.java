package com.im.getway.server.controller;

import com.im.file.server.domain.TableFileEntity;
import com.im.getway.server.base.BaseController;
import com.im.getway.server.base.JsonResult;
import com.im.getway.server.service.FileService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.server.tools.date.DateUtil;
import com.server.tools.util.ImageTools;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 文件上传控制类
 *
 * @author shiqilong
 */
@RestController
@RequestMapping("/file")
public class FileUploadController extends BaseController {

    @Autowired
    private FileService fileService;

    @Value("${im.local.update.img}")
    private String localImgProperties;


    /**
     * 一次上传多张图片
     *
     * @throws IOException
     * @throws JSchException
     * @throws SftpException
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "多张图片上传接口", notes = "文件上传接口详细描述")
    public JsonResult fileUpload(@RequestParam(value = "file", required = false) CommonsMultipartFile files[]){
        long now = System.currentTimeMillis();
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String datePath = DateUtil.formatDate1(new Date()) + "/";
            TableFileEntity tableFile = new TableFileEntity();
            String path = localImgProperties + "IM-FILE" + "/" + datePath;
            File f = new File(path);
            if (!f.exists())
                f.mkdirs();

            if (files.length != 0) {
                for (int i = 0; i < files.length; i++) {
                    String fileName = files[i].getOriginalFilename();
                    fileName = new String(fileName.getBytes("UTF-8"));
                    String fixname = fileName.substring(fileName.lastIndexOf(".") + 1);
                    String newFileName = UUID.randomUUID() + fileName;
                    try {
                        InputStream in = files[i].getInputStream();
                        FileOutputStream fos = new FileOutputStream(path + newFileName);
                        try {
                            byte[] buffer = new byte[102400];
                            int b = 0;
                            while ((b = in.read(buffer)) != -1) {
                                fos.write(buffer,0,b);
                            }
                            fos.close();
                            in.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            in.close();
                            fos.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tableFile.setFile_fixname("." + fixname);
                    tableFile.setFile_name(fileName);
                    tableFile.setFile_new_name(newFileName);
                    tableFile.setFile_path(datePath);
                    tableFile.setFile_size(ImageTools.getImgSize(new File(path + newFileName)));
                    tableFile.setFile_type(fixname);
                    tableFile.setPlatform("IM-FILE");
                    String imgUrl = fileService.saveFile(tableFile);
                    list.add(imgUrl);
                }
                map.put("resCode", "SUCCESS");
                map.put("resData", list);
            } else {
                map.put("resCode", "999");
                map.put("resData", "请选择文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("resCode", "999");
            map.put("resData", "系统异常：" + e.getMessage());
        }
        return getIntefaceData("",map,"/upload"+"/upload",now,"多张图片上传接口");
    }
}