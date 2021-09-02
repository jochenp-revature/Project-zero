package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.IUserDao;
import com.revature.repositories.UserDao;

public interface IUserService {
	
	IUserDao udao = new UserDao();
	
	User register(User u);
	
}
