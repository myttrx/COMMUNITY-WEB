package com.jos.community.module.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jos.community.module.entity.User;

public interface UserRepository extends  JpaSpecificationExecutor<User>,PagingAndSortingRepository<User, Integer>{

}
