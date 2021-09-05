package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.User;
import com.revature.repositories.UserDao;

public class UserService {
	
	private static Logger log = Logger.getLogger(UserService.class);
	
	public User register(User u) {
		log.info("Registering new user....");
		if(u.getId() !=0) {
			throw new RegisterUserFailedException("Received User Object did not have an id of 0");
		}
		UserDao udao = new UserDao();
		int generatedId = udao.insert(u);
		if(generatedId != -1 && generatedId != u.getId()) {
			u.setId(generatedId);
		} else {
			throw new RegisterUserFailedException("Failed to insert the user.");
		}
		log.info("Registration of user with id " + u.getId() + " was successful.");
		return u;
	}
	
	public void login(String username, String password) {
		UserDao udao = new UserDao();
		User u = new User();
		u = udao.findByUserName(username);
		if (u.getPassword().equals(password)) {
			System.out.println("Correct password.");
			// check role and supply appropriate menu
		} else {
			System.out.println("Incorrect password.");
		}
	}

	public void printAllUsers() {
		UserDao udao = new UserDao();
		List<User> users = udao.findAll();
		users.forEach(u -> System.out.println(u));
	}
	
	
}
