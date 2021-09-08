package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.revature.App;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDao implements IUserDao {
	
	private static Logger log = Logger.getLogger(UserDao.class);

	@Override
	public int insert(User u) { // should check for duplicates?
		try {
			Connection conn = ConnectionUtil.getConnection();
			String sql = "INSERT INTO jochenp.users (username, pwd, user_role) VALUES (?, ?, ?) RETURNING jochenp.users.id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setObject(3, u.getRole(), Types.OTHER);
			ResultSet rs;
			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				int id = rs.getInt("id");
				log.info("Successfully inserted User with id: " + id);
				return id;
			}
		} catch (SQLException e) {
			log.error("Unable to insert User.");
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public User findByUserName(String username) {
		User u = new User();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM jochenp.users WHERE jochenp.users.username = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			u.setId(rs.getInt("id"));
			u.setUsername(rs.getString("username"));
			u.setPassword(rs.getString("pwd"));
			u.setRole(Role.valueOf(rs.getString("user_role")));
		} catch (SQLException e) {
			log.warn("Failed to retrieve user with username " + username);
			e.printStackTrace();
			System.out.println("Username not found.  Please try again.");
			App.welcome();
		}
		return u;
	}

	@Override
	public List<User> findAll() {
		List<User> userList = new ArrayList<User>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM jochenp.users;";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id"); // you can specify the column number OR you can specify the column name
				String username = rs.getString("username");
				String password = rs.getString("pwd");
//				String role = rs.getString("user_role"); // again unsure how to get or set this enum!
				User u = new User(id, username, password, null, null);
				userList.add(u);
			}
		} catch (SQLException e) {
			log.warn("A SQL EXception occurred when querying all users.");
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public User findById(int id) {
		User u = new User();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT FROM jochenp.users WHERE jochenp.users.id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			u.setId(rs.getInt("id"));
			u.setUsername(rs.getString("username"));
			u.setPassword(rs.getString("pwd"));
			u.setRole(Role.valueOf(rs.getString("user_role")));
		} catch (SQLException e) {
			log.warn("Failed to retrieve user with id " + id);
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public void update(int id, String username, String password, String role) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE users SET username = ?, pwd = ?, user_role = ? WHERE id = ? RETURNING id;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setObject(3, role, Types.OTHER);
			stmt.setInt(4, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int returnedId = rs.getInt("id");
			log.info("Successfully updated user " + returnedId);
		} catch (SQLException e) {
			log.error("Failed to update user with id " + id);
			e.printStackTrace();
		}
	}

	@Override
	public boolean delete(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM users WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeQuery();
			log.info("Successfully deleted user " + id);
		} catch (SQLException e) {
			log.error("Failed to delete user with id " + id);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// TODO: add more IUserDao method implementations

}
