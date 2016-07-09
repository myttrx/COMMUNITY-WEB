package com.jos.community.module.repository;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jos.community.module.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/springContext/applicationContext.xml")
@Transactional
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	UserRepository userRep;

	@Rollback(false)
	@Test
	public void test() {
		User user = new User();
		user.setAccountName("test");
		user.setCreatedBy("test");
		user.setCreatedDate(new Date());
		user.setDeleteStatus(1);
		user.setDescription("test");
		user.setLocked("T");
		user.setModifiedBy("test");
		user.setModifiedDate(new Date());
		user.setPassword("123");
		user.setUserName("test");
		userRep.save(user);
	}
}
