package com.im.file.server.facade.impl;

import com.im.file.server.biz.TableFileService;
import com.im.file.server.domain.TableFileEntity;
import com.im.file.server.facade.TableFileFacade;
import com.im.file.server.page.PageBean;
import com.im.file.server.page.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tableFileFacade")
public class TableFileFacadeImpl implements TableFileFacade {
    @Autowired
    private TableFileService tableFileService;

    @Override
    public void saveFile(TableFileEntity TableFileEntity) {
        tableFileService.saveFile(TableFileEntity);
    }

    @Override
    public PageBean<TableFileEntity> selectFileAll(PageData pageData) {
        return this.tableFileService.selectFileAll(pageData);
    }

    @Override
    public TableFileEntity selectFileByIdentification(String id) {
        return this.tableFileService.selectFileByIdentification(id);
    }
}
