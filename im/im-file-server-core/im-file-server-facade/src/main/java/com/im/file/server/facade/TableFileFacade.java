package com.im.file.server.facade;


import com.im.file.server.domain.TableFileEntity;
import com.im.file.server.page.PageBean;
import com.im.file.server.page.PageData;

public interface TableFileFacade {
    public PageBean<TableFileEntity> selectFileAll(PageData pageData);

    public void saveFile(TableFileEntity TableFileEntity);

    public TableFileEntity selectFileByIdentification(String id);
}
