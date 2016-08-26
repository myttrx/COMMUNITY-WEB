package com.jos.community.system.module.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jos.community.system.module.entity.SecurityResource;

public interface ResourceRepository extends  JpaSpecificationExecutor<SecurityResource>,PagingAndSortingRepository<SecurityResource, Integer>{

}
