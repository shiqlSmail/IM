package com.im.getway.server.base;

import com.server.tools.exceptions.ArgumentException;
import com.server.tools.result.APIBaseResult;
import com.server.tools.result.SetAPIResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;


public class ResultResponse{
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	protected APIBaseResult getAPIResult(APIBaseResult result) {
		return result;
	}

	public JsonResult vailForm(BindingResult bindingResult){
		Map<String,Object> map = new HashMap<String, Object>();
		if(bindingResult.hasErrors()){
			log.info("参数不合法"+bindingResult.getFieldError().getDefaultMessage());
			return JsonResult.build(500,bindingResult.getFieldError().getDefaultMessage(),map);
		}
		return null;
	}
}
