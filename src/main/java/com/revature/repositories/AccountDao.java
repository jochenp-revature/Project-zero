package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.revature.models.Account;
import com.revature.util.ConnectionUtil;

public class AccountDao implements IAccountDao {

	private static Logger log = Logger.getLogger(AccountDao.class);

	@Override
	public int insert(Account a) {
		try {
			Connection conn = ConnectionUtil.getConnection();
			String sql = "INSERT INTO accounts (acc_owner) VALUES (?) RETURNING id;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, a.getAcc_owner());
			ResultSet rs;
			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				int id = rs.getInt("id");
				log.info("Successfully inserted account with id: " + id);
				return id;
			}
		} catch (SQLException e) {
			log.error("Unable to create account.");
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<Account> findAll() {
		List<Account> accList = new ArrayList<Account>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM jochenp.accounts";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int acc_owner = rs.getInt("acc_owner");
				boolean active = rs.getBoolean("active");
				Account a = new Account(id, balance, acc_owner, active);
				accList.add(a);
			}
		} catch (SQLException e) {
			log.warn("A SQL Exception occurred when querying all accounts");
			e.printStackTrace();
		}
		return accList;
	}

	@Override
	public Account findById(int id) {
		Account a = new Account();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT FROM accounts WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int returnedId = rs.getInt("id");
			double balance = rs.getDouble("balance");
			a.setId(returnedId);
			a.setBalance(balance);
		} catch (SQLException e) {
			log.warn("Failed to retrieve account with id " + id);
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public List<Account> findByOwner(int userId) { // IGNORES INACTIVE accounts!
		List<Account> ownedAccounts = new ArrayList<Account>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM jochenp.accounts WHERE acc_owner = ? AND active = true;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int acc_owner = rs.getInt("acc_owner");
				boolean active = rs.getBoolean("active");
				Account a = new Account(id, balance, acc_owner, active);
				// in the case that there are duplicates, DON'T add them to the arraylist
				if (!ownedAccounts.contains(a)) {
					ownedAccounts.add(a);
				}
			}
		} catch (SQLException e) {
			log.error("Failed to retrieve all accounts owned by user with id " + userId);
			e.printStackTrace();
		}
		return ownedAccounts;
	}
	
	public List<Account> findInactive() {
		List<Account> pendingAccounts = new ArrayList<Account>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM jochenp.accounts WHERE active = false;";
			PreparedStatement stmt = conn.prepareStatement(sql); // how do we set the ?
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int acc_owner = rs.getInt("acc_owner");
				boolean active = rs.getBoolean("active");
				Account a = new Account(id, balance, acc_owner, active);
				// in the case that there are duplicates, DON'T add them to the arraylist
				if (!pendingAccounts.contains(a)) {
					pendingAccounts.add(a);
				}
			}
		} catch (SQLException e) {
			log.error("Failed to retrieve all inactive accounts.");
			e.printStackTrace();
		}
		return pendingAccounts;
	}

	public double getBalance(int accId) { // VALIDATE WITH USER ID!!!!
		double balance = 0;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT balance FROM jochenp.accounts WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, accId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			balance = rs.getDouble("balance");
		} catch (SQLException e) {
			log.warn("Failed to retrieve balance of account id " + accId);
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public void updateBalance(int accId, double balance) { // validate with uid!!!!
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE accounts SET balance = ? WHERE id = ? RETURNING balance;"; /// add active requirement!
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, balance);
			stmt.setInt(2, accId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			if (rs.getDouble("balance") == balance) {
				log.info("Successfully updated balance of account " + accId + " to " + balance);
				System.out.println("Successfully updated balance of account " + accId + " to " + balance);
				// go back to customer menu?
			} else {
				log.warn("Problem updating balance of account " + accId);
				System.out.println("Problem updating balance of account " + accId + "Please try again.");
				// go back to customer menu?
			}
		} catch (SQLException e) {
			log.error("Failed to update account with id " + accId);
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean activate(int id) {
		boolean active = false;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE accounts SET active = true WHERE id = ? RETURNING active;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			if (rs.getBoolean("active") == true) {
				active = true;
				log.info("Successfully activated account " + id);
			} else {
				active = false;
				log.warn("Failed to update account with id " + id);
			}
		} catch (SQLException e) {
			log.error("Failed to update account with id " + id);
			e.printStackTrace();
			active = false;
		}
		return active;
	}

	@Override
	public boolean delete(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM accounts WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeQuery();
			log.info("Successfully deleted account " + id);
		} catch (SQLException e) {
			log.error("Failed to delete account with id " + id);
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
