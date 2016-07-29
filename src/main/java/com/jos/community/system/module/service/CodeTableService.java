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
import com.jos.community.system.module.model.CodeTableModel;
import com.jos.community.system.module.repository.CodeTableRepository;
import com.jos.community.system.module.vo.CodeTableGridRecordVo;
import com.jos.community.utils.DateUtils;
import com.jos.community.utils.DynamicSpecifications;
import com.jos.community.utils.SearchFilter;
import com.jos.community.utils.SearchFilter.Operator;
import com.jos.community.utils.StrUtils;

@Service
@Transactional(readOnly=true)
public class CodeTableService {

	@Autowired
	private CodeTableRepository codeTableRepo;
	
	public Page<CodeTableGridRecordVo> search(Pageable pageable,CodeTableModel codeTableModel){
		Collection<SearchFilter> filters = null;
		Page<CodeTableGridRecordVo> page = null;
		Specification<CodeTable> spec = null;
		if (codeTableModel!=null) {
			filters =new ArrayList<SearchFilter>();
			SearchFilter filter = null;
			if (StrUtils.isNotBlank(codeTableModel.getCodeTableType())) {
				filter = new SearchFilter("code", Operator.EQ, codeTableModel.getCodeTableType());
				filters.add(filter);
			}
			if (StrUtils.isNotBlank(codeTableModel.getCode())) {
				filter = new SearchFilter("code", Operator.LIKE, codeTableModel.getCode());
				filters.add(filter);
			}
			if (StrUtils.isNotBlank(codeTableModel.getDescription())) {
				filter = new SearchFilter("description", Operator.LIKE, codeTableModel.getDescription());
				filters.add(filter);
			}
			spec = DynamicSpecifications.bySearchFilter(filters,CodeTable.class);
		}
		Page<CodeTable> result = this.codeTableRepo.findAll(spec, pageable);
		if (result!=null && result.getContent().size()>0) {
			List<CodeTable> resultList = result.getContent();
			List<CodeTableGridRecordVo> gridRecordVoList = new ArrayList<CodeTableGridRecordVo>();
			for(CodeTable codeTable : resultList){
				CodeTableGridRecordVo gridRecordVo = new CodeTableGridRecordVo();
				gridRecordVo.setCodeTableId(codeTable.getId());
				gridRecordVo.setCode(codeTable.getCode());
				gridRecordVo.setDescription(codeTable.getDescription());
				gridRecordVo.setModifiedDate(DateUtils.convertDateToStr(codeTable.getModifiedDate()));
				gridRecordVo.setType(codeTable.getType());
				gridRecordVoList.add(gridRecordVo);
			}
			page = new PageImpl<CodeTableGridRecordVo>(gridRecordVoList,pageable,result.getNumberOfElements());
		}else {
			page = new PageImpl<CodeTableGridRecordVo>(new ArrayList<CodeTableGridRecordVo>(), pageable, 0);
		}
		return page;
	}
}
