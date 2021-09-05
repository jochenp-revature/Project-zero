package com.revature.repositories;

import java.util.List;

import com.revature.models.Account;

public interface IAccountDao {
	
	int insert(Account a); // return the primary key of the account inserted
	List<Account> findAll(); // returns list of account objects...
	Account findById(int id);
	List<Account> findByOwner(int userId); // because a user could have multiple accounts
	double getBalance(int accId);
	void updateBalance(int accId, double balance);
	boolean delete(int id);

	

}
