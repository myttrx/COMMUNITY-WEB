package com.jos.community.system.module.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jos.community.system.module.entity.SecurityResourceTree;

public interface ResourceTreeRepository extends  JpaSpecificationExecutor<SecurityResourceTree>,PagingAndSortingRepository<SecurityResourceTree, Integer>{

}
