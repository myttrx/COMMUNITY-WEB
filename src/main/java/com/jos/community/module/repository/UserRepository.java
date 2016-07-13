package com.jos.community.module.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.jos.community.module.entity.User;

public interface UserRepository extends  JpaSpecificationExecutor<User>,PagingAndSortingRepository<User, Integer>{

	@Query(" select u from User u where u.userName=:userName ")
	public User findUserByName(@Param("userName") String userName);
}
