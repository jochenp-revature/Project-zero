package com.revature.services;

import com.revature.models.Account;
import com.revature.repositories.AccountDao;

public class AccountService {
	
	public boolean open(int userId) {  // create inactive account based on userId
		Account a = new Account();
		a.setAcc_owner(userId);
		AccountDao adao = new AccountDao();
		int result = adao.insert(a);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void deposit(int userId, int accId, double deposit) { // all these need userId or full user objects!!
		AccountDao adao = new AccountDao();
		double newBalance = deposit + adao.getBalance(accId);
		adao.updateBalance(accId, newBalance); // need to verify this further?
	}
	
	public void withdraw(int accId, double withdraw) {
		AccountDao adao = new AccountDao();
		double newBalance = adao.getBalance(accId) - withdraw;
		if (newBalance < 0) {
			System.out.println("Insufficient funds.");
		} else
		adao.updateBalance(accId, newBalance); // need to verify this further?
	}
	
	public void transfer(int sourceId, int targetId, double amount) {
		AccountDao adao = new AccountDao();
		double newSourceBalance = adao.getBalance(sourceId) - amount;
		double newTargetBalance = adao.getBalance(targetId) + amount;
		if (newSourceBalance < 0) {
			System.out.println("Insufficient funds.");
		} else
		adao.updateBalance(sourceId, newSourceBalance);
		adao.updateBalance(targetId, newTargetBalance);// need to verify this further?
	}

}
