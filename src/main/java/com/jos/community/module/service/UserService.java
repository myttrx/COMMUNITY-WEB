package com.jos.community.module.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jos.community.module.entity.User;
import com.jos.community.module.repository.UserRepository;
import com.jos.community.utils.StrUtils;
import com.jos.security.core.LoginUserDetails;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	public LoginUserDetails findUserDetail(String userName){
		LoginUserDetails loginUserDetails = null;
		if (StrUtils.isNotBlank(userName)) {
			User user = this.findUserByName(userName);
			if (user!=null) {
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
				authorities.add(authority);
				if (userName.equals("ADMIN")) {
					authority = new SimpleGrantedAuthority("ROLE_ADMIN");
					authorities.add(authority);
				}
				loginUserDetails = new LoginUserDetails(user.getUserName(), user.getPassword(), authorities);
			}
		}
		return loginUserDetails;
	}
	
	public User findUserByName(String userName){
		User user = this.userRepo.findUserByName(userName);
		return user;
	}
}
