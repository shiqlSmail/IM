package com.im.file.server.repository;

import com.im.file.server.domain.TableFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TableFileRepository {

	List<TableFileEntity> selectFileAll();

	Integer selectFileCount();

	void saveFile(TableFileEntity tableFileBO);

	TableFileEntity selectFileByIdentification(TableFileEntity tableFileBO);
}
