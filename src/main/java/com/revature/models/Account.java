package com.revature.models;

import java.io.Serializable;

public class Account implements Serializable {
		
	private static final long serialVersionUID = -7919958173228399875L;
	private int id; // primary key
	private double balance; // in SQL this is represented by the NUMERIC data-type
	private int acc_owner;
	private boolean active;
	
	public Account() {
		super();
	}
	
	public Account(int id, double balance, int acc_owner, boolean active) { // fields should match those in the db!
		super();
		this.id = id;
		this.balance = balance;
		this.acc_owner = acc_owner;
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getAcc_owner() {
		return acc_owner;
	}

	public void setAcc_owner(int acc_owner) {
		this.acc_owner = acc_owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + "]";
	}
	
}
