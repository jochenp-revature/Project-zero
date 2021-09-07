package com.revature;

import java.util.ArrayList;
import java.util.Scanner;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.AccountDao;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class App {

	public static void main(String[] args) {
		welcome();
	}
	
	static void welcome() {
		System.out.println("  /$$$$$$  /$$$$$$$$ /$$      /$$        /$$$$$$  /$$       /$$$$$$\n"
						+ " /$$__  $$|__  $$__/| $$$    /$$$       /$$__  $$| $$      |_  $$_/\n"
						+ "| $$  \\ $$   | $$   | $$$$  /$$$$      | $$  \\__/| $$        | $$  \n"
						+ "| $$$$$$$$   | $$   | $$ $$/$$ $$      | $$      | $$        | $$  \n"
						+ "| $$__  $$   | $$   | $$  $$$| $$      | $$      | $$        | $$  \n"
						+ "| $$  | $$   | $$   | $$\\  $ | $$      | $$    $$| $$        | $$  \n"
						+ "| $$  | $$   | $$   | $$ \\/  | $$      |  $$$$$$/| $$$$$$$$ /$$$$$$\n"
						+ "|__/  |__/   |__/   |__/     |__/       \\______/ |________/|______/");
		System.out.println("Welcome to the ATM CLI, press 1 to login or 2 to register:");
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt(); // need input mismatch try catch!!!!
		while (input != 1 && input != 2) {
			System.out.println("Please enter 1 to login or 2 to register");
			input = scan.nextInt();
		}
		if (input == 1 ) {
			loginScript();
		} else if (input == 2) {
			registrationScript();
		}
	}
	
	public static void loginScript() {
		System.out.println("To login, please enter your username:");
		Scanner scan = new Scanner(System.in);
		String username = scan.nextLine();
		System.out.println("Please enter your password:");
		String password = scan.nextLine();
		System.out.println("Thank you.  One moment please...");
		UserService userv = new UserService();
		userv.login(username, password);
	}
	
	public static void registrationScript() {
		System.out.println("To register, please enter a username:");
		Scanner scan = new Scanner(System.in);
		String username = scan.nextLine();
		System.out.println("Please enter a password:");
		String password = scan.nextLine();
		System.out.println("Thank you.  One moment please...");
		User u = new User(0, username, password, Role.Customer, new ArrayList<Account>());
		UserService userv = new UserService();
		u = userv.register(u);
		if (u.getId() != 0) {
			System.out.println("Successfully registered user " + username + "."
					+ "Your first account is pending approval.");
		}
		loginScript();
	}
	
	public static void customerMenu(User u) {
		System.out.println("Welcome, " + u.getUsername() + ", here are your accounts with the ATM CLI:");
		AccountDao adao = new AccountDao();
		System.out.println(adao.findByOwner(u.getId()));
		System.out.println("Press 1 to deposit, 2 to withdraw, 3 to transfer, 4 to open a new account, "
				+ "or 5 to exit:");
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();
		while (input != 1 && input != 2 && input != 3 && input != 4 && input != 5) {
			System.out.println("Press 1 to deposit, 2 to withdraw, 3 to transfer, 4 to open a new account, "
				+ "or 5 to exit:");
			input = scan.nextInt();
		}
		if (input == 1) {
			depositScript();
		} else if (input == 2) {
			withdrawScript();
		} else if (input == 3) {
			transferScript();
		} else if (input == 4) {
			openNewAccountScript();
		} else if (input == 5) {
			System.out.println("Have a nice day.");
			welcome();
		}
		
	}
	
	public static void depositScript() {
		Scanner scan = new Scanner(System.in);
		System.out.println("To make a deposit, please enter the account id:");
		int id = scan.nextInt(); // check that id exists?
		System.out.println("Please enter the amount you wish to deposit into account id " + id + ":");
		double amount = scan.nextDouble();
		while (amount <= 0) {
			System.out.println("Your deposit must be greater than zero:");
			amount = scan.nextDouble();
		}
		scan.close();
		AccountService aserv = new AccountService();
		aserv.deposit(id, amount); // should return some confirmation?
	}
	
	public static void withdrawScript() {
		Scanner scan = new Scanner(System.in);
		System.out.println("To make a withdrawal, please enter the account id:");
		int id = scan.nextInt(); // check that id exists?
		System.out.println("Please enter the amount you wish to withdraw into account id " + id + ":");
		double amount = scan.nextDouble();
		while (amount <= 0) {
			System.out.println("Your withdrawal must be greater than zero:");
			amount = scan.nextDouble();
		}
		scan.close();
		AccountService aserv = new AccountService();
		aserv.withdraw(id, amount); // should return some confirmation?
	}
	
	public static void transferScript() {
		Scanner scan = new Scanner(System.in);
		System.out.println("To transfer funds, please enter the source account id:");
		int id1 = scan.nextInt(); // check that id exists?
		System.out.println("Now enter the target account id:");
		int id2 = scan.nextInt(); // check that id exists?
		System.out.println("Please enter the amount you wish to transfer into account id " + id2 + ":");
		double amount = scan.nextDouble();
		while (amount <= 0) {
			System.out.println("Your withdrawal must be greater than zero:");
			amount = scan.nextDouble();
		}
		scan.close();
		AccountService aserv = new AccountService();
		aserv.transfer(id1, id2, amount); // should return some confirmation?
	}
	
	public static void openNewAccountScript() {
		
	}
	
	public static void employeeMenu(User u) {
		
	}

	public static void adminMenu(User u) {
		
	}
		
//		User u = new User(0, username, password, Role.Customer, new ArrayList<Account>());
//		
//		UserService userv = new UserService();
//		System.out.println(userv.register(u));
		
//		User u = new User();
//		u.setRole(Role.Customer);
//		System.out.println(u.getRole());
		
		// need a login script
		// login menu
//		UserService userv = new UserService();
//		userv.printAllUsers();
//		System.out.println(userv.login("Batman", "pass"));
	
//		AccountService aserv = new AccountService();
//		System.out.println(aserv.transfer(3, 4, 1000));
	
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

