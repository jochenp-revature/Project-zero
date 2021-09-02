package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.User;

public class UserService implements IUserService {
	
	private static Logger log = Logger.getLogger(UserService.class);
	
	@Override
	public User register(User u) { // if we insert a user from the console it doens't have an id yet
		// takes in User object
		log.info("Registering new user....");
	
		if(u.getId() !=0) {			
			// something is wrong......
			// throw custom exception specifying exactly what is wrong
			throw new RegisterUserFailedException("Received User Object did not have an id of 0");
		}
		
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
		// call some dao methods that check if this user exists, maybe return an 
		// array of all and iterate through each one to check is username and password are the sem
		
		// use the fiundByUsername dao method, then check that that user has the same password as the one passed through here
		
		return null;
	}

	
	
	public void printAllUsers() {
		
		List<User> users = udao.findAll();
			
		users.forEach(u -> System.out.println(u));
		// you could also use an enahnced for loop 
	}
	
	
	
	
	
}
