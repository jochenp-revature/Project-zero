package com.revature.services;

import org.apache.log4j.Logger;
import com.revature.models.Account;
import com.revature.repositories.AccountDao;

public class AccountService {
	
	private static Logger log = Logger.getLogger(UserService.class);
	
	public Account deposit(int accId, double amount) {
		AccountDao adao = new AccountDao();
		double balance = amount + adao.getBalance(accId);
		adao.updateBalance(accId, balance); // need to verify this further?
		Account a = new Account(accId, balance);
		return a;
	}
	
	public Account withdraw(Account id, double amount) {
		return null;
	}
	
	public Account transfer(Account sourceId, Account targetId, double amount) {
		return null;
	}

}
