package com.im.getway.server.service;


import com.im.file.server.domain.TableFileEntity;
import com.im.file.server.page.PageBean;
import com.im.file.server.page.PageData;
import com.im.getway.server.request.TableFileRequest;

public interface FileService {

    public String saveFile(TableFileEntity tableFile);

    public PageBean<TableFileRequest> findFiles(PageData pageData);

    public String selectFileById(String id);
}
