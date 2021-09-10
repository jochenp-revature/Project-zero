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
	
	public static void welcome() {
		System.out.println("  /$$$$$$  /$$$$$$$$ /$$      /$$        /$$$$$$  /$$       /$$$$$$\n"
						+ " /$$__  $$|__  $$__/| $$$    /$$$       /$$__  $$| $$      |_  $$_/\n"
						+ "| $$  \\ $$   | $$   | $$$$  /$$$$      | $$  \\__/| $$        | $$  \n"
						+ "| $$$$$$$$   | $$   | $$ $$/$$ $$      | $$      | $$        | $$  \n"
						+ "| $$__  $$   | $$   | $$  $$$| $$      | $$      | $$        | $$  \n"
						+ "| $$  | $$   | $$   | $$\\  $ | $$      | $$    $$| $$        | $$  \n"
						+ "| $$  | $$   | $$   | $$ \\/  | $$      |  $$$$$$/| $$$$$$$$ /$$$$$$\n"
						+ "|__/  |__/   |__/   |__/     |__/       \\______/ |________/|______/");
		System.out.print("Welcome to the ATM CLI.  ");
		Scanner scan = new Scanner(System.in);
		int input = 0;
		do {
			System.out.println("Please enter 1 to login or 2 to register:");
		    while (!scan.hasNextInt()) {
		        System.out.println("Please enter 1 to login or 2 to register:");
		        scan.next();
		    }
		    input = scan.nextInt();
		} while (input != 1 && input != 2);
		if (input == 1 ) {
			loginScript();
		} else if (input == 2) {
			registrationScript();
		}
		scan.close();
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
			System.out.println("Successfully registered user " + username + ".  "
					+ "Your first account is pending approval.");
		}
		loginScript();
	}
	
	public static void customerMenu(User u) {  // need to handle inactive accounts differently!!!!
		List<Account> accounts = new ArrayList<Account>();
		AccountDao adao = new AccountDao();
		accounts = adao.findByOwner(u.getId());
		if (accounts.isEmpty()) { // need to handle inactive accounts differently!!!!
			System.out.println("Your first account is still pending approval.");
		} else {
			System.out.println(u.getUsername() + ", here are your active accounts with the ATM CLI:");
			System.out.println(accounts);
		}
		Scanner scan = new Scanner(System.in);
		int input = 0;
		do {
			System.out.println("Press 1 to deposit, 2 to withdraw, 3 to transfer, 4 to open a new account, "
					+ "or 5 to exit:");
		    while (!scan.hasNextInt()) {
		        System.out.println("Press 1 to deposit, 2 to withdraw, 3 to transfer, 4 to open a new account, "
						+ "or 5 to exit:");
		        scan.next();
		    }
		    input = scan.nextInt();
		} while (input != 1 && input != 2 && input != 3 && input != 4 && input != 5);
		
		if (input == 1) {
			depositScript(u);
		} else if (input == 2) {
			withdrawScript(u);
		} else if (input == 3) {
			transferScript(u);
		} else if (input == 4) {
			openNewAccountScript(u);
		} else if (input == 5) {
			System.out.println("Have a nice day.");
			welcome();
		}
		
	}
	
	public static void employeeMenu(User u) {
		Scanner scan = new Scanner(System.in);
		int input = 0;
		do {
			System.out.println("Employee menu: press 1 for user lookup, 2 for accounts lookup, 3 to approve applications, or 4 to exit:");
		    while (!scan.hasNextInt()) {
		        System.out.println("Please press 1 for user lookup, 2 for accounts lookup, 3 to approve applications, or 4 to exit:");
		        scan.next();
		    }
		    input = scan.nextInt();
		} while (input != 1 && input != 2 && input != 3 && input != 4);
		
		if (input == 1) {
			userLookup(u);
		} else if (input == 2) {
			accountsLookup(u);
		} else if (input == 3) {
			approveService(u);
		} else {
			System.out.println("Have a nice day."); // exit
			welcome();
		}
	}
	
	public static void adminMenu(User u) {
		Scanner scan = new Scanner(System.in);
		int input = 0;
		do {
			System.out.println("Admin menu: press 1 for user lookup, 2 for accounts lookup, 3 to approve applications, "
					+ "\n4 to deposit, 5 to withdraw, 6 to transfer, 7 to delete account, or 8 to exit:");
		    while (!scan.hasNextInt()) {
		    	System.out.println("Press 1 for user lookup, 2 for accounts lookup, 3 to approve applications, "
						+ "\n4 to deposit, 5 to withdraw, 6 to transfer, 7 to delete account, or 8 to exit:");
		        scan.next();
		    }
		    input = scan.nextInt();
		} while (input != 1 && input != 2 && input != 3 && input != 4 && input != 5 && input != 6 && input != 7 && input != 8);
		
		if (input == 1) {
			userLookup(u);
		} else if (input == 2) {
			accountsLookup(u);
		} else if (input == 3) {
			approveService(u);
		} else if (input == 4) {
			depositScript(u);
		} else if (input == 5) {
			withdrawScript(u);
		} else if (input == 6) {
			transferScript(u);
		} else if (input == 7) {
			deleteScript(u);
		} else {
			System.out.println("Have a nice day."); // exit
			welcome();
		}
	}
	
	public static void depositScript(User u) { // should take a user id
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
		aserv.deposit(u.getId(), accId, amount); // should return some confirmation?
		System.out.println("Successfully deposited " + amount + " to account " + accId + ".");
		customerMenu(u);
	}
	
	public static void withdrawScript(User u) {
		Scanner scan = new Scanner(System.in);
		System.out.println("To make a withdrawal, please enter the account id:");
		int id = scan.nextInt(); // check that id exists?
		System.out.println("Please enter the amount you wish to withdraw into account id " + id + ":");
		double amount = scan.nextDouble();
		while (amount <= 0) {
			System.out.println("Your withdrawal must be greater than zero:");
			amount = scan.nextDouble();
		}
		AccountService aserv = new AccountService();
		aserv.withdraw(id, amount); // should return some confirmation?
		System.out.println("Withdraw successful, please login again to make another transaction.");
		customerMenu(u);
	}
	
	public static void transferScript(User u) {
		Scanner scan = new Scanner(System.in);
		System.out.println("To transfer funds, please enter the source account id:");
		int id1 = scan.nextInt(); // check that id exists and belongs to user?
		System.out.println("Now enter the target account id:");
		int id2 = scan.nextInt(); // check that id exists and belongs to user?
		System.out.println("Please enter the amount you wish to transfer into account id " + id2 + ":");
		double amount = scan.nextDouble();
		while (amount <= 0) {
			System.out.println("Your withdrawal must be greater than zero:");
			amount = scan.nextDouble();
		}
		AccountService aserv = new AccountService();
		aserv.transfer(id1, id2, amount); // should return some confirmation?
		System.out.println("Successfully updated accounts " + id1 + " and " + id2);
		welcome();
	}
	
	public static void openNewAccountScript(User u) {
		AccountService aserv = new AccountService();
		aserv.open(u.getId());
		System.out.println("Your account application was recieved and is pending approval.");
		customerMenu(u);
	}
	
	public static void userLookup(User u) {
		System.out.println("Please enter the username you wish to lookup:");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		UserDao udao = new UserDao();
		User u1 = udao.findByUserName(input);
		System.out.println(u1);
		employeeMenu(u);
	}

	public static void accountsLookup(User u) {
		System.out.println("Please enter the id of the user whose accounts you wish to find:");
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();
		AccountDao adao = new AccountDao();
		System.out.println(adao.findByOwner(input));
		employeeMenu(u);
	}

	public static void approveService(User u) {
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
			log.info("Account " + accId + " was approved by user " + u.getId());
		} else {
			System.out.println("There was a problem activating account " + accId + ". Please try again.");
		}
		adminMenu(u);
	}
	
	public static void deleteScript(User u) {
		System.out.println("Please enter the account number to be deleted:");
		Scanner scan = new Scanner(System.in);
		int accId = scan.nextInt();
		AccountDao adao = new AccountDao();
		boolean confirm = adao.delete(accId);
		if (confirm == true) {
			System.out.println("Account " + accId + " has been deleted.");
			log.info("Account " + accId + " was deleted by user " + u.getId());
		} else {
			System.out.println("There was a problem deleting account " + accId + ". Please try again.");
		}
		adminMenu(u);
	}
	
}
