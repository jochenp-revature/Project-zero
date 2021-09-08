package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.App;
import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.Role;
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
		AccountService aserv = new AccountService();
		aserv.open(generatedId);
		return u;
	}
	
	public void login(String username, String password) {
		UserDao udao = new UserDao();
		User u = new User();
		u = udao.findByUserName(username);
		if (u.getPassword().equals(password)) {
			log.info("User " + username + " has successfully logged in.");
			if (u.getRole().equals(Role.Admin) ) {
				App.adminMenu(u);
			} else if (u.getRole().equals(Role.Employee)) {
				App.employeeMenu(u);
			} else {
				App.customerMenu(u);
			}
		} else {
			System.out.println("Incorrect password.");
			log.warn("Login attempt for user " + username + " failed.");
			App.welcome();
		}
	}

	public void printAllUsers() {
		UserDao udao = new UserDao();
		List<User> users = udao.findAll();
		users.forEach(u -> System.out.println(u));
	}
	
	
}
