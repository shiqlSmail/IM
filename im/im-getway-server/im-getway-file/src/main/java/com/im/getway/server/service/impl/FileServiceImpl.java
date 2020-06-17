package com.im.getway.server.service.impl;

import com.im.file.server.domain.TableFileEntity;
import com.im.file.server.facade.TableFileFacade;
import com.im.file.server.page.PageBean;
import com.im.file.server.page.PageData;
import com.im.getway.server.request.TableFileRequest;
import com.im.getway.server.service.FileService;
import com.server.tools.date.DateUtil;
import com.server.tools.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    public TableFileFacade tableFileFacade;

    @Value("${im.view.img}")
    private String imViewImg;

    @Value("${im.local.reading.img}")
    private String localImgProperties;

    @Value("${im.default.image}")
    private String imDefaultImage;
    @Value("${im.default.context}")
    private String imDefaultContext;

    @Override
    //@Async
    public String saveFile(TableFileEntity tableFile) {
        String Identification = UUIDUtil.getOrderIdByUUId();
        tableFile.setIdentification(Identification);
        tableFile.setPlatform(tableFile.getPlatform());
        tableFile.setCreatetime(DateUtil.getDateTime(new Date()));
        tableFile.setAuther("IM-FILE");
        tableFileFacade.saveFile(tableFile);

        TableFileEntity TableFileEntity = tableFileFacade.selectFileByIdentification(Identification);
        return imViewImg+"/"+"IM-FILE"+"/"+TableFileEntity.getFile_path()+"/"+TableFileEntity.getFile_new_name();
    }

    @Override
    public PageBean<TableFileRequest> findFiles(PageData pageData) {
        PageBean<TableFileRequest> TableFileRequestPageBean = new PageBean<>();
        List<TableFileRequest> TableFileRequestArrayList = new ArrayList<>();

        PageBean<TableFileEntity> listFile = tableFileFacade.selectFileAll(pageData);
        if(listFile.getItems().size() > 0){
            for (TableFileEntity tableFileEntity : listFile.getItems()){
                TableFileRequest TableFileRequest = new TableFileRequest();
                TableFileRequest.setFileName(imViewImg+"/"+tableFileEntity.getPlatform()+"/"+tableFileEntity.getFile_path()+tableFileEntity.getFile_new_name());
                TableFileRequestArrayList.add(TableFileRequest);
            }
        }
        TableFileRequestPageBean.setItems(TableFileRequestArrayList);
        return TableFileRequestPageBean;
    }

    @Override
    public String selectFileById(String id) {
        TableFileEntity tableFileEntity = tableFileFacade.selectFileByIdentification(id);
        if(null == tableFileEntity){
            return "";
        }
        return imViewImg+"/"+tableFileEntity.getPlatform()+"/"+tableFileEntity.getFile_path()+tableFileEntity.getFile_new_name();
    }
}
