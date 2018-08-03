package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.BoardUser;
import com.revature.util.ConnectionUtil;

public class BoardUserDao {
	public BoardUser getUser(String username, String userpass) {
		PreparedStatement ps = null;
		BoardUser b = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from boarduser where username=? and userpass=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, userpass);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				int checkAdminStatus = rs.getInt("isAdmin");
				if (checkAdminStatus == 1) {
					b = new BoardUser(username, userpass, rs.getString("userdesc"), true);
				} else {
					b = new BoardUser(username, userpass, rs.getString("userdesc"), false);
				}
			} else {
				System.out.println("username or password is incorrect, please try again");
				return b;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return b;
	}
	
	public BoardUser getUser(String username) {
		PreparedStatement ps = null;
		BoardUser b = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from boarduser where username=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				int checkAdminStatus = rs.getInt("isAdmin");
				if (checkAdminStatus == 1) {
					b = new BoardUser(username, rs.getString("userpass"), rs.getString("userdesc"), true);
				} else {
					b = new BoardUser(username, rs.getString("userpass"), rs.getString("userdesc"), false);
				}
			} else {
				System.out.println("username or password is incorrect, please try again");
				return b;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return b;
	}
	
	public static boolean validate(String name, String pass) {
		boolean status = false;
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from boarduser where username = ? and userpass = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			
			status = rs.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return status;
	}
	
	public void updateUser(BoardUser b) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update boarduser set userdesc=? where username=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, b.getUserdesc());
			ps.setString(2, b.getUsername());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public List<BoardUser> getAllUsers() {
		List<BoardUser> users = new ArrayList<>();
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select username, userdesc, isAdmin from boarduser";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String username = rs.getString("username");
				String userdesc = rs.getString("userdesc");
				boolean isAdmin;
				if (rs.getInt("isAdmin") == 1) isAdmin = true;
				else isAdmin = false;
				
				users.add(new BoardUser(username, "redacted", userdesc, isAdmin));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return users;
	}
}
