package com.jos.community.system.module.controller;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jos.community.module.service.MessageService;
import com.jos.community.system.module.entity.CodeTableType;
import com.jos.community.system.module.model.CodeTableModel;
import com.jos.community.system.module.service.CodeTableService;
import com.jos.community.system.module.vo.CodeTableGridRecordVo;
import com.jos.community.utils.Constant;
import com.jos.community.utils.StrUtils;
import com.jos.core.web.JqGrid;
import com.jos.core.web.JsonResponse;
import com.jos.core.web.SelectHelper;

@Controller
@RequestMapping("/system/codeTable")
public class CodeTableCtrl {
	
	protected static Logger logger = LoggerFactory.getLogger(CodeTableCtrl.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	@Qualifier(value = "codeTableValidator")
	private Validator codeTableValidator;
	@Autowired
	private MessageService messageService;
	@Autowired
	private CodeTableService codeTableService;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(codeTableValidator);
	}
	
	@RequestMapping(value = "/init.shtml", method = RequestMethod.GET)
	public String init(Model model){
		CodeTableModel codeTableModel = new CodeTableModel();
		model.addAttribute("codeTableModel", codeTableModel);
		model.addAttribute("codeTableTypeOpts", SelectHelper.getOptions(true, CodeTableType.class, ""));
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
		CodeTableModel codeTableModel = null;
		try {
			objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			codeTableModel = objectMapper.readValue(searchForm, CodeTableModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Pageable pageable = null;
		if(StrUtils.isNotBlank(sidx)){
			pageable = new PageRequest(page - 1, rows,sord.equals(Constant.JqGridSord.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC,sidx);
		}else {
			pageable = new PageRequest(page - 1, rows);
		}
		Page<CodeTableGridRecordVo> datas = codeTableService.search(pageable,codeTableModel);
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
	
	@RequestMapping(value="save.shtml",method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse save(@ModelAttribute @Validated CodeTableModel codeTableModel, BindingResult result){
		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			jsonResponse.setValidationFailStatus();
			jsonResponse.setErrors(result.getFieldErrors());
			return jsonResponse;
		}
		try {
			this.codeTableService.save(codeTableModel);
			jsonResponse.setSuccess();
			jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.RECORD_SAVE_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setFail();
			jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.SYSTEM_ERROR));
		}
		return jsonResponse;
	}
	
	@RequestMapping(value="{id}/findCodeTable.shtml",method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse findCodeTable(@PathVariable("id") String id){
		JsonResponse jsonResponse = new JsonResponse();
		if (StrUtils.isNotBlank(id)) {
			try {
				CodeTableModel codeTableModel = this.codeTableService.findById(id);
				jsonResponse.setData(codeTableModel);
				jsonResponse.setSuccess();
			} catch (Exception e) {
				e.printStackTrace();
				jsonResponse.setFail();
				jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.SYSTEM_ERROR));
			}
		}else {
			jsonResponse.setFail();
			jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.SYSTEM_ERROR));
		}
		return jsonResponse;
	}
	
}
