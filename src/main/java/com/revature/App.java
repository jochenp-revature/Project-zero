package com.revature;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.AccountDao;
import com.revature.repositories.UserDao;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class App {
	
	private static Logger log = Logger.getLogger(App.class);

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
		System.out.println(u.getUsername() + ", here are your accounts with the ATM CLI:");
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
			depositScript(u.getId());
		} else if (input == 2) {
			withdrawScript(u.getId());
		} else if (input == 3) {
			transferScript(u.getId());
		} else if (input == 4) {
			openNewAccountScript();
		} else if (input == 5) {
			System.out.println("Have a nice day.");
			welcome();
		}
		
	}
	
	public static void depositScript(int userId) { // should take a user id
		Scanner scan = new Scanner(System.in);
		System.out.println("To make a deposit, please enter the account id:");
		int accId = scan.nextInt(); // check that account exists and belongs to user?
		System.out.println("Please enter the amount you wish to deposit into account id " + accId + ":");
		double amount = scan.nextDouble();
		while (amount <= 0) {
			System.out.println("Your deposit must be greater than zero:");
			amount = scan.nextDouble();
		}
		AccountService aserv = new AccountService();
		aserv.deposit(userId, accId, amount); // should return some confirmation?
		System.out.println("Successfully deposited " + amount + " to account " + accId + ".");
		// return to customerMenu?
	}
	
	public static void withdrawScript(int userId) {
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
	
	public static void transferScript(int userId) {
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
	
	public static void employeeMenu(int userId) {
		System.out.println("Employee menu: press 1 for user lookup, 2 for accounts lookup, 3 to approve applications, or 4 to exit:");
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();
		while (input != 1 && input != 2 && input != 3 && input != 4) {
			System.out.println("Press 1 for user lookup, 2 for accounts lookup, 3 to approve applications, or 4 to exit:");
			input = scan.nextInt();
		}
		if (input == 1) {
			userLookup(userId);
		} else if (input == 2) {
			accountsLookup(userId);
		} else if (input == 3) {
			approveService(userId);
		} else {
			System.out.println("Have a nice day."); // exit
			welcome();
		}
	}
	
	public static void userLookup(int userId) {
		System.out.println("Please enter the user you wish to lookup:");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		UserDao udao = new UserDao();
		User u = udao.findByUserName(input);
		System.out.println(u);
		employeeMenu(userId);
	}

	public static void accountsLookup(int userId) {
		System.out.println("Please enter the user whose accounts you wish to find:");
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();
		AccountDao adao = new AccountDao();
		System.out.println(adao.findByOwner(input));
		employeeMenu(userId);
	}

	public static void approveService(int userId) {
		AccountDao adao = new AccountDao();
		List<Account> accounts = adao.findInactive();
		System.out.println("Here is a list of all inactive accounts pending approval:");
		System.out.println(accounts);
		System.out.println("Please enter the account number to approve:");
		Scanner scan = new Scanner(System.in);
		int accId = scan.nextInt();
		boolean active = adao.activate(accId);
		if (active == true) {
			System.out.println("Account " + accId + " has been approved.");
			log.info("Account " + accId + " was approved by user " + userId);
		} else {
			System.out.println("There was a problem activating account " + accId + ". Please try again.");
		}
	}

	public static void adminMenu(int userId) {
		System.out.println("Admin menu: press 1 for user lookup, 2 for accounts lookup, 3 to approve applications, "
				+ "4 to deposit, 5 to withdraw, 6 to transfer, 7 to delete account, or 8 to exit:");
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();
		while (input != 1 && input != 2 && input != 3 && input != 4) {
			System.out.println("Press 1 for user lookup, 2 for accounts lookup, 3 to approve applications, "
					+ "4 to deposit, 5 to withdraw, 6 to transfer, 7 to delete account, or 8 to exit:");
			input = scan.nextInt();
		}
		if (input == 1) {
			userLookup(userId);
		} else if (input == 2) {
			accountsLookup(userId);
		} else if (input == 3) {
			approveService(userId);
		} else if (input == 4) {
			depositScript(userId);
		} else if (input == 5) {
			withdrawScript(userId);
		} else if (input == 6) {
			transferScript(userId);
		} else if (input == 7) {
			deleteScript(userId);
		} else {
			System.out.println("Have a nice day."); // exit
			welcome();
		}
	}
	
	public static void deleteScript(int userId) {
		System.out.println("Please enter the account number to be deleted:");
		Scanner scan = new Scanner(System.in);
		int accId = scan.nextInt();
		AccountDao adao = new AccountDao();
		boolean confirm = adao.delete(accId);
		if (confirm == true) {
			System.out.println("Account " + accId + " has been deleted.");
			log.info("Account " + accId + " was deleted by user " + userId);
		} else {
			System.out.println("There was a problem deleting account " + accId + ". Please try again.");
		}
		adminMenu(userId);
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

