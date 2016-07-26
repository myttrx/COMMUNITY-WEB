package com.jos.community.system.module.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jos.community.system.module.entity.CodeTable;

public interface CodeTableRepository extends  JpaSpecificationExecutor<CodeTable>,PagingAndSortingRepository<CodeTable, Integer>{

}
