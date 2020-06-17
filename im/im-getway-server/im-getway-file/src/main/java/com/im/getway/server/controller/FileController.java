package com.im.getway.server.controller;

import com.im.file.server.page.PageBean;
import com.im.file.server.page.PageData;
import com.im.getway.server.base.BaseController;
import com.im.getway.server.base.JsonResult;
import com.im.getway.server.request.FileIdRequest;
import com.im.getway.server.request.TableFileRequest;
import com.im.getway.server.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;

    @Value("${im.default.image}")
    private String imDefaultImage;

    /**
     * 文件列表
     *
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value="文件列表接口", notes="文件接口详细描述")
    public JsonResult fileList(@RequestBody PageData pageData) {
        long now = System.currentTimeMillis();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            PageBean<TableFileRequest> listFile = fileService.findFiles(pageData);
            List<Object> TableFileRequestList = new ArrayList<>();
            if (null == listFile || listFile.getItems().size() == 0) {
                Map<String,Object> map = new HashMap<>();
                map.put("type","2");
                map.put("files",imDefaultImage);
                TableFileRequestList.add(map);
            } else {
                for (TableFileRequest TableFileRequest : listFile.getItems()) {
                    String fixname = TableFileRequest.getFileName().substring(TableFileRequest.getFileName().lastIndexOf(".") + 1);
                    Map<String,Object> map = new HashMap<>();
                    if(fixname.contains("mp4")){
                        map.put("type","1");
                        map.put("files",TableFileRequest.getFileName());
                    }else if(fixname.contains("png") || fixname.contains("jpg") || fixname.contains("gif")){
                        map.put("type","2");
                        map.put("files",TableFileRequest.getFileName());
                    }else if(fixname.contains("doc") || fixname.contains("docx") || fixname.contains("xls") || fixname.contains("xlsx")){
                        map.put("type","3");
                        map.put("files",TableFileRequest.getFileName());
                    }else if(fixname.contains("ico")){
                        map.put("type","4");
                        map.put("files",TableFileRequest.getFileName());
                    }else{
                        map.put("type","5");
                        map.put("files",TableFileRequest.getFileName());
                    }
                    TableFileRequestList.add(map);
                }
            }
            resultMap.put("redCode", "SUCCESS");
            resultMap.put("resData", TableFileRequestList);
        }catch(Exception e){
            resultMap.put("redCode", "FILE_LIST_ERROR_999");
            resultMap.put("resData", "系统异常："+e.getMessage());
        }
        return getIntefaceData(pageData,resultMap,"/file"+"/list",now,"文件列表接口");
    }

    /**
     * 文件详情
     *
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/object", method = RequestMethod.POST)
    @ApiOperation(value="文件详情接口", notes="文件列表接口详细描述")
    public JsonResult fileObject(@RequestBody FileIdRequest fileIdRequest) {
        long now = System.currentTimeMillis();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            String file = fileService.selectFileById(fileIdRequest.getId());
            resultMap.put("redCode", "SUCCESS");
            resultMap.put("resData", file);
        }catch(Exception e){
            resultMap.put("redCode", "FILE_LIST_ERROR_999");
            resultMap.put("resData", "系统异常："+e.getMessage());
        }
        return getIntefaceData(fileIdRequest,resultMap,"/file"+"/object",now,"文件详情接口");
    }
}
