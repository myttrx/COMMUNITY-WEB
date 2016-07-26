package com.jos.community.system.module.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jos.community.system.module.entity.CodeTable;
import com.jos.community.system.module.repository.CodeTableRepository;
import com.jos.community.system.module.vo.CodeTableGridRecordVo;

@Service
@Transactional(readOnly=true)
public class CodeTableService {

	@Autowired
	private CodeTableRepository codeTableRepo;
	
	public Page<CodeTableGridRecordVo> search(Pageable pageable){
		Page<CodeTable> result = this.codeTableRepo.findAll(pageable);
		Page<CodeTableGridRecordVo> page = null;
		if (result!=null && result.getContent().size()>0) {
			List<CodeTable> resultList = result.getContent();
			List<CodeTableGridRecordVo> gridRecordVoList = new ArrayList<CodeTableGridRecordVo>();
			for(CodeTable codeTable : resultList){
				CodeTableGridRecordVo gridRecordVo = new CodeTableGridRecordVo();
				gridRecordVo.setCodeTableId(codeTable.getId());
				gridRecordVo.setCode(codeTable.getCode());
				gridRecordVo.setDescription(codeTable.getDescription());
				gridRecordVo.setModifiedDate("");
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
