package com.revature;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.AccountDao;
import com.revature.repositories.IAccountDao;
import com.revature.repositories.UserDao;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;

public class App {

	public static void main(String[] args) {
		
		// registration script something like this...
		System.out.println("To register, please enter a username:");
		Scanner scan = new Scanner(System.in);
		String username = scan.next();
		System.out.println("Please enter a password:");
		String password = scan.next();
		scan.close();
		
		User u = new User(0, username, password, Role.Customer, new ArrayList<Account>());
		
		UserService userv = new UserService();
		System.out.println(userv.register(u));
//
//		UserDao udao = new UserDao();
//
//		int returnedPK = ud.insert(u1);
//
//		// test to see if the dummy user we inserted was persisted and returned
//		// a primary key generated by the DB.
//		System.out.println(returnedPK);
		
//		IAccountDao adao = new AccountDao();

//		List<Account> accounts = adao.findAll();
//		System.out.println(accounts);
//
//		System.out.println(adao.findByOwner(5));
		
		
		
		// scanner gets the username, and password.... and we build a new User obj from the console 
		
		// call our service method to REGISTER it
		
	}

}
