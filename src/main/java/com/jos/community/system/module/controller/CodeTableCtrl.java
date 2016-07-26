package com.jos.community.system.module.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jos.community.system.module.service.CodeTableService;
import com.jos.community.system.module.vo.CodeTableGridRecordVo;
import com.jos.community.utils.Constant;
import com.jos.community.utils.StrUtils;
import com.jos.core.web.JqGrid;

@Controller
@RequestMapping("/system/codeTable")
public class CodeTableCtrl {
	
	protected static Logger logger = LoggerFactory.getLogger(CodeTableCtrl.class);
	
	@Autowired
	private CodeTableService codeTableService;
	
	@RequestMapping(value = "/init.shtml", method = RequestMethod.GET)
	public String init(Model model){
		return "module/system/code_table_init";
	}
	
	@RequestMapping(value = "/search.shtml", method = RequestMethod.POST)
	@ResponseBody
	public String search(@RequestParam(value = "format", defaultValue = "json") String format,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sidx", defaultValue = "") String sidx,
			@RequestParam(value = "sord", defaultValue = "") String sord,
			@RequestParam(value = "searchForm", defaultValue = "") String searchForm){
		
		Pageable pageable = null;
		if(StrUtils.isNotBlank(sidx)){
			pageable = new PageRequest(page - 1, rows,sord.equals(Constant.JqGridSord.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC,sidx);
		}else {
			pageable = new PageRequest(page - 1, rows);
		}
		Page<CodeTableGridRecordVo> datas = codeTableService.search(pageable);
		JqGrid<CodeTableGridRecordVo> jqGrid = new JqGrid<CodeTableGridRecordVo>();
		jqGrid.setPage(page);
		jqGrid.setRows(rows);
		jqGrid.setCurrentPageRecords(datas.getNumberOfElements());
		jqGrid.setGriddata(datas.getContent());
		jqGrid.setTotalpages(datas.getTotalPages());
		jqGrid.setTotalrecords(datas.getTotalElements());
		jqGrid.addCols("codeTableId","type","code","description","modifiedDate");
		String json = jqGrid.toJson();
		return json;
	}
}
