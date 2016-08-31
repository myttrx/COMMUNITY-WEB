package com.jos.community.system.module.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jos.community.module.service.MessageService;
import com.jos.community.system.module.entity.CodeTable;
import com.jos.community.system.module.model.ResourceModel;
import com.jos.community.system.module.model.ResourceTreeModel;
import com.jos.community.system.module.service.CodeTableService;
import com.jos.community.system.module.service.ResourceService;
import com.jos.community.system.module.validator.ResourceTreeValidator;
import com.jos.community.system.module.validator.ResourceValidator;
import com.jos.community.system.module.vo.ResourceGridRecordVo;
import com.jos.community.system.module.vo.ResourceTreeGridRecordVo;
import com.jos.community.utils.Constant;
import com.jos.community.utils.StrUtils;
import com.jos.core.web.JqGrid;
import com.jos.core.web.JsonResponse;
import com.jos.core.web.SelectHelper;

@Controller
@RequestMapping("/system/resource")
public class SecurityResourceCtrl {
	
	protected static Logger logger = LoggerFactory.getLogger(SecurityResourceCtrl.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	@Qualifier(value = "resourceValidator")
	private Validator resourceValidator;
	
	@Autowired
	@Qualifier(value = "resourceTreeValidator")
	private Validator resourceTreeValidator;
	
	@Autowired
	private MessageService messageService;
	@Autowired
	private CodeTableService codeTableService;
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value = "/init.shtml", method = RequestMethod.GET)
	public String init(Model model){
		List<CodeTable> resourceTypeList = this.codeTableService.findByType(Constant.CodeTableType.RESOURCE);
		String resourceTypeOpts = SelectHelper.getOptions(true, resourceTypeList, "code", "code", "");
		ResourceModel resourceModel = new ResourceModel();
		model.addAttribute("resourceModel", resourceModel);
		model.addAttribute("resourceTypeOpts", resourceTypeOpts);
		return "module/system/resource_init";
	}
	
	@RequestMapping(value = "/searchResourceTree.shtml", method = RequestMethod.POST)
	@ResponseBody
	public String searchResourceTree(@RequestParam(value = "format", defaultValue = "json") String format,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sidx", defaultValue = "") String sidx,
			@RequestParam(value = "sord", defaultValue = "") String sord,
			@RequestParam(value = "filters", defaultValue = "") String filters,
			@RequestParam(value = "resourceId", defaultValue = "0") int resourceId,
			HttpServletRequest request){
//		Set<Object> set = request.getParameterMap().keySet();
//		for(Object key : set){
//			System.out.println("key : "+key +" value : ");
//			Object o = request.getParameterMap().get(key);
//			System.out.println("value : "+o.toString());
//		}

		Pageable pageable = null;
		if(StrUtils.isNotBlank(sidx)){
			pageable = new PageRequest(page - 1, rows,sord.equals(Constant.JqGridSord.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC,sidx);
		}else {
			pageable = new PageRequest(page - 1, rows);
		}
		Page<ResourceTreeGridRecordVo> datas = this.resourceService.searchResourceTree(pageable,resourceId);
		JqGrid<ResourceTreeGridRecordVo> jqGrid = new JqGrid<ResourceTreeGridRecordVo>();
		jqGrid.setPage(page);
		jqGrid.setRows(rows);
		jqGrid.setCurrentPageRecords(datas.getNumberOfElements());
		jqGrid.setGriddata(datas.getContent());
		jqGrid.setTotalpages(datas.getTotalPages());
		jqGrid.setTotalrecords(datas.getTotalElements());
		jqGrid.addCols("nodeName","nodeDesc","parentNodeName","nodeOrder","treeId");
		String json = jqGrid.toJson();
		return json;
	}
	
	@RequestMapping(value = "/search.shtml", method = RequestMethod.POST)
	@ResponseBody
	public String search(@RequestParam(value = "format", defaultValue = "json") String format,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sidx", defaultValue = "") String sidx,
			@RequestParam(value = "sord", defaultValue = "") String sord,
			@RequestParam(value = "filters", defaultValue = "") String filters,
			@RequestParam(value = "searchForm", defaultValue = "") String searchForm){

		ResourceModel resourceModel = null;
		try {
			objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			resourceModel = objectMapper.readValue(searchForm, ResourceModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Pageable pageable = null;
		if(StrUtils.isNotBlank(sidx)){
			pageable = new PageRequest(page - 1, rows,sord.equals(Constant.JqGridSord.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC,sidx);
		}else {
			pageable = new PageRequest(page - 1, rows);
		}
		Page<ResourceGridRecordVo> datas = this.resourceService.search(pageable,resourceModel);
		JqGrid<ResourceGridRecordVo> jqGrid = new JqGrid<ResourceGridRecordVo>();
		jqGrid.setPage(page);
		jqGrid.setRows(rows);
		jqGrid.setCurrentPageRecords(datas.getNumberOfElements());
		jqGrid.setGriddata(datas.getContent());
		jqGrid.setTotalpages(datas.getTotalPages());
		jqGrid.setTotalrecords(datas.getTotalElements());
		jqGrid.addCols("resourceName","resourceType","resourceContent","resourceDesc","resourceOrder","modifiedDate","modifiedBy","resourceId");
		String json = jqGrid.toJson();
		return json;
	}
	
	@Validated(ResourceValidator.class)
	@RequestMapping(value="save.shtml",method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse save(@ModelAttribute ResourceModel resourceModel, BindingResult result){
		resourceValidator.validate(resourceModel, result);
		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			jsonResponse.setValidationFailStatus();
			jsonResponse.setErrors(result.getFieldErrors());
			return jsonResponse;
		}
		try {
			this.resourceService.save(resourceModel);
			jsonResponse.setSuccess();
			jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.RECORD_SAVE_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setFail();
			jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.SYSTEM_ERROR));
		}
		return jsonResponse;
	} 
	
	@RequestMapping(value="deleteResource.shtml",method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteResource(@RequestParam(value = "ids", defaultValue = "") String ids){
		JsonResponse jsonResponse = new JsonResponse();
		try {
			this.resourceService.deleteByIds(ids);
			jsonResponse.setSuccess();
			jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.RECORD_DELETE_SUCCESS));
		} catch (Exception e) {
			jsonResponse.setFail();
			jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.SYSTEM_ERROR));
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	@RequestMapping(value = "{id}/edit.shtml", method = RequestMethod.GET)
	public String edit(Model model,@PathVariable("id") String id){
		
		ResourceModel resourceModel = this.resourceService.findById(id);
		ResourceTreeModel resourceTreeModel = new ResourceTreeModel();
		resourceTreeModel.setResourceId(resourceModel.getResourceId());
		List<CodeTable> resourceTypeList = this.codeTableService.findByType(Constant.CodeTableType.RESOURCE);
		String resourceTypeOpts = SelectHelper.getOptions(true, resourceTypeList, "code", "code", resourceModel.getResourceType());
		model.addAttribute("resourceModel", resourceModel);
		model.addAttribute("resourceTypeOpts", resourceTypeOpts);
		model.addAttribute("resourceTreeModel", resourceTreeModel);
		return "module/system/resource_edit";
	}
	
	@RequestMapping(value="saveTree.shtml",method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveTree(@ModelAttribute ResourceTreeModel resourceTreeModel, BindingResult result){
		resourceTreeValidator.validate(resourceTreeModel, result);
		JsonResponse jsonResponse = new JsonResponse();
		if (result.hasErrors()) {
			jsonResponse.setValidationFailStatus();
			jsonResponse.setErrors(result.getFieldErrors());
			return jsonResponse;
		}
		try {
			this.resourceService.saveResourceTree(resourceTreeModel);
			jsonResponse.setSuccess();
			jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.RECORD_SAVE_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setFail();
			jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.SYSTEM_ERROR));
		}
		return jsonResponse;
	}
	
	@RequestMapping(value = "/searchTreeNode.shtml", method = RequestMethod.POST)
	@ResponseBody
	public String searchTreeNode(@RequestParam(value = "format", defaultValue = "json") String format,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sidx", defaultValue = "") String sidx,
			@RequestParam(value = "sord", defaultValue = "") String sord,
			@RequestParam(value = "filters", defaultValue = "") String filters){

		Pageable pageable = null;
		if(StrUtils.isNotBlank(sidx)){
			pageable = new PageRequest(page - 1, rows,sord.equals(Constant.JqGridSord.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC,sidx);
		}else {
			pageable = new PageRequest(page - 1, rows);
		}
		Page<ResourceTreeGridRecordVo> datas = this.resourceService.searchTreeNode(pageable);
		JqGrid<ResourceTreeGridRecordVo> jqGrid = new JqGrid<ResourceTreeGridRecordVo>();
		jqGrid.setPage(page);
		jqGrid.setRows(rows);
		jqGrid.setCurrentPageRecords(datas.getNumberOfElements());
		jqGrid.setGriddata(datas.getContent());
		jqGrid.setTotalpages(datas.getTotalPages());
		jqGrid.setTotalrecords(datas.getTotalElements());
		jqGrid.addCols("nodeName","resourceName","resourceContent","parentNodeName","nodeOrder","treeId");
		String json = jqGrid.toJson();
		return json;
	}
	
	@RequestMapping(value = "/searchChildrenTreeNode.shtml", method = RequestMethod.POST)
	@ResponseBody
	public String searchChildrenTreeNode(@RequestParam(value = "format", defaultValue = "json") String format,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sidx", defaultValue = "") String sidx,
			@RequestParam(value = "sord", defaultValue = "") String sord,
			@RequestParam(value = "filters", defaultValue = "") String filters,
			@RequestParam(value = "parentTreeId", defaultValue = "0") int parentTreeId){

		Pageable pageable = null;
		if(StrUtils.isNotBlank(sidx)){
			pageable = new PageRequest(page - 1, rows,sord.equals(Constant.JqGridSord.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC,sidx);
		}else {
			pageable = new PageRequest(page - 1, rows);
		}
		Page<ResourceTreeGridRecordVo> datas = this.resourceService.searchChildrenTreeNode(pageable,parentTreeId);
		JqGrid<ResourceTreeGridRecordVo> jqGrid = new JqGrid<ResourceTreeGridRecordVo>();
		jqGrid.setPage(page);
		jqGrid.setRows(rows);
		jqGrid.setCurrentPageRecords(datas.getNumberOfElements());
		jqGrid.setGriddata(datas.getContent());
		jqGrid.setTotalpages(datas.getTotalPages());
		jqGrid.setTotalrecords(datas.getTotalElements());
		jqGrid.addCols("nodeName","nodeDesc","resourceName","nodeOrder","treeId");
		String json = jqGrid.toJson();
		return json;
	}
	
	@RequestMapping(value="deleteResourceTree.shtml",method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteResourceTree(@RequestParam(value = "ids", defaultValue = "") String ids){
		JsonResponse jsonResponse = new JsonResponse();
		try {
			this.resourceService.deleteResourceTreeByIds(ids);
			jsonResponse.setSuccess();
			jsonResponse.setSingleMessage(this.messageService.getMessage(Constant.MsgCode.RECORD_DELETE_SUCCESS));
		} catch (Exception e) {
			jsonResponse.setFail();
			jsonResponse.setSingleMessage(e.getMessage());
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	@RequestMapping(value = "/loadResourceTreeData.shtml", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse loadResourceTreeData(){
		JsonResponse response = new JsonResponse();
		response.setSuccess();
		String data = "{'刑侦': {'name': '刑侦','type': 'folder','additionalParameters': {'id': '1','children': {"
				+ "'痕迹检验': {'name': '痕迹检验','type': 'item','additionalParameters': {'id': '10'}},"
				+ "'声像技术': {'name': '声像技术','type': 'item','additionalParameters': {'id': '9'}}"
				+ "}}}}";
		response.setData(data);
		return response;
	}
}
