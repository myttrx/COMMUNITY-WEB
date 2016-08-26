package com.jos.community.system.module.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jos.community.system.module.entity.CodeTable;
import com.jos.community.system.module.entity.SecurityResource;
import com.jos.community.system.module.entity.SecurityResourceTree;
import com.jos.community.system.module.model.ResourceModel;
import com.jos.community.system.module.repository.ResourceRepository;
import com.jos.community.system.module.repository.ResourceTreeRepository;
import com.jos.community.system.module.vo.CodeTableGridRecordVo;
import com.jos.community.system.module.vo.ResourceGridRecordVo;
import com.jos.community.system.module.vo.ResourceTreeGridRecordVo;
import com.jos.community.utils.Constant;
import com.jos.community.utils.DateUtils;
import com.jos.community.utils.DynamicSpecifications;
import com.jos.community.utils.SearchFilter;
import com.jos.community.utils.StrUtils;
import com.jos.community.utils.SearchFilter.Operator;
import com.jos.security.core.EntityUtils;

@Service
@Transactional
public class ResourceService {
	
	@Autowired
	private ResourceTreeRepository resourceTreeRepo;
	@Autowired
	private ResourceRepository resourceRepo;
	@Autowired
	private CodeTableService codeTableService;
	
	public Page<ResourceTreeGridRecordVo> searchResourceTree(Pageable pageable){
		Page<ResourceTreeGridRecordVo> page = null;
		Page<SecurityResourceTree> result = this.resourceTreeRepo.findAll(pageable);
		if (result!=null && result.getContent().size()>0) {
			List<SecurityResourceTree> resultList = result.getContent();
			List<ResourceTreeGridRecordVo> gridRecordVoList = new ArrayList<ResourceTreeGridRecordVo>();
			for(SecurityResourceTree resourceTree : resultList){
				ResourceTreeGridRecordVo gridRecordVo = new ResourceTreeGridRecordVo();
				gridRecordVo.setNodeName(resourceTree.getNodeName());
				gridRecordVo.setResourceContent(resourceTree.getSecurityResource().getResourceContent());
				gridRecordVo.setParentNodeName(resourceTree.getParentResourceTree()!=null ? resourceTree.getParentResourceTree().getNodeName() : "");
				gridRecordVo.setNodeOrder(resourceTree.getNodeOrder());
				gridRecordVo.setTreeId(resourceTree.getTreeId());
				gridRecordVoList.add(gridRecordVo);
			}
			page = new PageImpl<ResourceTreeGridRecordVo>(gridRecordVoList,pageable,result.getNumberOfElements());
		}else {
			page = new PageImpl<ResourceTreeGridRecordVo>(new ArrayList<ResourceTreeGridRecordVo>(), pageable, 0);
		}
		return page;
	}
	
	public Page<ResourceGridRecordVo> search(Pageable pageable,ResourceModel resourceModel){
		Collection<SearchFilter> filters = null;
		Specification<SecurityResource> spec = null;
		if (resourceModel!=null) {
			filters =new ArrayList<SearchFilter>();
			SearchFilter filter = null;
			if (StrUtils.isNotBlank(resourceModel.getResourceName())) {
				filter = new SearchFilter("resourceName", Operator.LIKE, resourceModel.getResourceName());
				filters.add(filter);
			}
			if (StrUtils.isNotBlank(resourceModel.getResourceType())) {
				filter = new SearchFilter("resourceType", Operator.EQ, resourceModel.getResourceType());
				filters.add(filter);
			}
			if (StrUtils.isNotBlank(resourceModel.getResourceContent())) {
				filter = new SearchFilter("resourceContent", Operator.LIKE, resourceModel.getResourceContent());
				filters.add(filter);
			}
			if (StrUtils.isNotBlank(resourceModel.getResourceDesc())) {
				filter = new SearchFilter("resourceDesc", Operator.LIKE, resourceModel.getResourceDesc());
				filters.add(filter);
			}
			spec = DynamicSpecifications.bySearchFilter(filters,SecurityResource.class);
		}
		
		Page<ResourceGridRecordVo> page = null;
		Page<SecurityResource> result = this.resourceRepo.findAll(spec,pageable);
		if (result!=null && result.getContent().size()>0) {
			List<SecurityResource> resultList = result.getContent();
			List<ResourceGridRecordVo> gridRecordVoList = new ArrayList<ResourceGridRecordVo>();
			for(SecurityResource resource : resultList){
				ResourceGridRecordVo gridRecordVo = new ResourceGridRecordVo();
				gridRecordVo.setResourceName(resource.getResourceName());
				gridRecordVo.setResourceType(this.codeTableService.findDesc(Constant.CodeTableType.RESOURCE, resource.getResourceType()));
				gridRecordVo.setResourceContent(resource.getResourceContent());
				gridRecordVo.setResourceDesc(resource.getResourceDesc());
				gridRecordVo.setResourceOrder(resource.getResourceOrder());
				gridRecordVo.setModifiedBy(resource.getModifiedBy());
				gridRecordVo.setModifiedDate(DateUtils.convertDateToStr(resource.getModifiedDate(),DateUtils.DATETIME_FORMAT));
				gridRecordVo.setResourceId(resource.getId());
				gridRecordVoList.add(gridRecordVo);
			}
			page = new PageImpl<ResourceGridRecordVo>(gridRecordVoList,pageable,result.getNumberOfElements());
		}else {
			page = new PageImpl<ResourceGridRecordVo>(new ArrayList<ResourceGridRecordVo>(), pageable, 0);
		}
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceModel resourceModel){
		SecurityResource securityResource = new SecurityResource();
		EntityUtils.insertValue(securityResource);
		securityResource.setResourceContent(resourceModel.getResourceContent());
		securityResource.setResourceDesc(resourceModel.getResourceDesc());
		securityResource.setResourceName(resourceModel.getResourceName());
		securityResource.setResourceOrder(Integer.parseInt(resourceModel.getResourceOrder()));
		securityResource.setResourceType(resourceModel.getResourceType());
		this.resourceRepo.save(securityResource);
	}
	
	@Transactional(readOnly = false)
	public void deleteByIds(String ids)throws Exception{
		if (StrUtils.isNotBlank(ids)) {
			String [] idArray = ids.split(",");
			for(String id : idArray){
				this.resourceRepo.delete(Integer.parseInt(id));
			}
		}else {
			throw new Exception("Call function ResourceService.deleteByIds ,Id can not be null.");
		}
	}
}
