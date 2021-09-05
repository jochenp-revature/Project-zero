package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.User;
import com.revature.repositories.UserDao;

public class UserService {
	
	private static Logger log = Logger.getLogger(UserService.class);
	
	public User register(User u) { // if we insert a user from the console it doens't have an id yet
		// takes in User object
		log.info("Registering new user....");
	
		if(u.getId() !=0) {			
			// something is wrong......
			// throw custom exception specifying exactly what is wrong
			throw new RegisterUserFailedException("Received User Object did not have an id of 0");
		}
		
		UserDao udao = new UserDao();
		// If the user's id IS == to 0....
		// call the insert() method from the UserDao
		int generatedId = udao.insert(u);
	
		if(generatedId != -1 && generatedId != u.getId()) {
			// fairly certain that the INSERT operation was successful
			u.setId(generatedId);
		} else {
			throw new RegisterUserFailedException("Failed to insert the user.");
		}
		
		log.info("Registration of user with id " + u.getId() + " was successful.");
		return u;
		
	}
	
	public User login(String username, String password) {
		// use the findByUsername dao method, then check that that user has the same password as the one passed through here
		// should also check role to which menu to supply?
		UserDao udao = new UserDao();
		User u = new User();
		u = udao.findByUserName(username);
		if (u.getPassword().equals(password)) {
			System.out.println("Correct password.");
		} else {
			System.out.println("Incorrect password.");
		}
		return u;
	}

	
	
	public void printAllUsers() {
		UserDao udao = new UserDao();
		List<User> users = udao.findAll();
			
		users.forEach(u -> System.out.println(u));
		// you could also use an enhanced for loop 
	}
	
	
	
	
	
}
