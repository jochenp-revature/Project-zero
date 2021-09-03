package com.revature.repositories;

import java.util.List;

import com.revature.models.User;

/**
 * DAO is a data access object
 * 
 * it is used to separate our persistence logic from our business logic and outline all CRUD functionality on an object
 */
public interface IUserDao { // all CRUD methods
	
	int insert(User u); // we aim to return the primary key of the user inserted into the DB
//	User findByUserName(String username); // READ method
//	List<User> findAll();
//	User findById(int id);
//	boolean update(User u); // takes in a whoel user and update's its record in the database
//	boolean delete(int id); 
	
	
	// TODO: add other abstract methods

}
