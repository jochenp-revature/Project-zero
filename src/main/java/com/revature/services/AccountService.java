package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import com.revature.models.Account;
import com.revature.repositories.AccountDao;

public class AccountService {
	
	public Account open(Account a) {
		return a;
	}
	
	public int deposit(int userId, int accId, double deposit) {
		AccountDao adao = new AccountDao();
		double newBalance = deposit + adao.getBalance(accId);
		adao.updateBalance(accId, newBalance); // need to verify this further?
		return accId;
		// reload customer menu here?
	}
	
	public Account withdraw(int accId, double withdraw) {
		AccountDao adao = new AccountDao();
		double newBalance = adao.getBalance(accId) - withdraw;
		if (newBalance < 0) {
			System.out.println("Insufficient funds.");
		} else
		adao.updateBalance(accId, newBalance); // need to verify this further?
		Account a = new Account(accId, newBalance);
		return a;
	}
	
	public List<Account> transfer(int sourceId, int targetId, double amount) {
		AccountDao adao = new AccountDao();
		double newSourceBalance = adao.getBalance(sourceId) - amount;
		double newTargetBalance = adao.getBalance(targetId) + amount;
		if (newSourceBalance < 0) {
			System.out.println("Insufficient funds.");
		} else
		adao.updateBalance(sourceId, newSourceBalance);
		adao.updateBalance(targetId, newTargetBalance);// need to verify this further?
		Account a = new Account(sourceId, newSourceBalance);
		Account b = new Account(targetId, newTargetBalance);
		List<Account> list = new ArrayList<Account>();
		list.add(a);
		list.add(b);
		return list;
	}

}
