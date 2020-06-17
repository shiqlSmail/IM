package com.im.file.server.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TableFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer file_id;
	private String file_name;
	private String file_path;
	private String file_type;
	private String file_size;
	private String file_fixname;
	private String file_new_name;
	private String platform;
	private String auther;
	private String createtime;
	private String Identification;
}