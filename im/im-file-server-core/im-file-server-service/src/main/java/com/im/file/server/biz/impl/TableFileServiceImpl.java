package com.im.file.server.biz.impl;

import com.github.pagehelper.PageHelper;
import com.im.file.server.biz.TableFileService;
import com.im.file.server.dataconfig.DataSourceType;
import com.im.file.server.dataconfig.MyDataSource;
import com.im.file.server.domain.TableFileEntity;
import com.im.file.server.page.PageBean;
import com.im.file.server.page.PageConvert;
import com.im.file.server.page.PageData;
import com.im.file.server.repository.TableFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("tableFileService")
public class TableFileServiceImpl implements TableFileService {
	@Autowired
	private TableFileRepository tableFileRepository;

	@Override
	@MyDataSource(DataSourceType.Master)
	public void saveFile(TableFileEntity TableFileEntity) {
		this.tableFileRepository.saveFile(TableFileEntity);
	}

	@Override
	@MyDataSource(DataSourceType.Master)
	public PageBean<TableFileEntity> selectFileAll(PageData pageData) {
		PageBean<TableFileEntity> page = new PageBean<TableFileEntity>();
		if (null != pageData.getCurrentPage()) {
			page.setCurrentPage(pageData.getCurrentPage());
		}
		if (null != pageData.getPageSize()) {
			page.setPageSize(pageData.getPageSize());
		}
		//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
		PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
		List<TableFileEntity> listFile = tableFileRepository.selectFileAll();
		Integer countFile = tableFileRepository.selectFileCount();

		//返回数据，这样返回才会有分页的参数
		return PageConvert.getPageBean(page,listFile,countFile);

	}

	@Override
	@MyDataSource(DataSourceType.Master)
	public TableFileEntity selectFileByIdentification(String id) {
		TableFileEntity tableFileEntity = new TableFileEntity();
		tableFileEntity.setIdentification(id);
		return this.tableFileRepository.selectFileByIdentification(tableFileEntity);
	}
}
