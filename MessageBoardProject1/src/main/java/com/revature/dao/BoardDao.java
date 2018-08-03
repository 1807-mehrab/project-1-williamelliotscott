package com.revature.dao;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.BoardUser;
import com.revature.model.Topic;
import com.revature.util.ConnectionUtil;

public class BoardDao {

	public List<Topic> getTopics(BoardUser user) {
		List<Topic> result = new ArrayList<>();
		String name = user.getUsername();
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from topic where topicAuth=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String topicName = rs.getString("topicName");
				String topicDesc = rs.getString("topicDesc");
				int flags = rs.getInt("flags");
				int id = rs.getInt("topicID");
				Blob image = rs.getBlob("image");
				
				result.add(new Topic(topicName, topicDesc, user, flags, id, image));
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	public List<Topic> getTopics() {
		List<Topic> result = new ArrayList<>();
		PreparedStatement ps = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from topic";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String topicName = rs.getString("topicName");
				String topicDesc = rs.getString("topicDesc");
				String topicAuth = rs.getString("topicAuth");
				int flags = rs.getInt("flags");
				int id = rs.getInt("topicID");
				BoardUserDao dao = new BoardUserDao();
				BoardUser b = dao.getUser(topicAuth);
				Blob image = rs.getBlob("image");
				
				result.add(new Topic(topicName, topicDesc, b, flags, id, image));
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	public List<Topic> getFlaggedTopics() {
		List<Topic> result = new ArrayList<>();
		PreparedStatement ps = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from topic where flags > 0 order by flags desc";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String topicName = rs.getString("topicName");
				String topicDesc = rs.getString("topicDesc");
				String topicAuth = rs.getString("topicAuth");
				int flags = rs.getInt("flags");
				int id = rs.getInt("topicID");
				BoardUserDao dao = new BoardUserDao();
				BoardUser b = dao.getUser(topicAuth);
				Blob image = rs.getBlob("image");
				
				result.add(new Topic(topicName, topicDesc, b, flags, id, image));
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	public Topic getOneTopic(String name) {
		Topic result = null;
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from topic where topicName=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String topicDesc = rs.getString("topicDesc");
				String topicAuth = rs.getString("topicAuth");
				int flags = rs.getInt("flags");
				int id = rs.getInt("topicID");
				BoardUserDao dao = new BoardUserDao();
				BoardUser b = dao.getUser(topicAuth);
				Blob image = rs.getBlob("image");
				
				result = new Topic(name, topicDesc, b, flags, id, image);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	public Topic getOneTopic(int id) {
		Topic result = null;
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from topic where topicID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String topicDesc = rs.getString("topicDesc");
				String topicAuth = rs.getString("topicAuth");
				int flags = rs.getInt("flags");
				String name = rs.getString("topicName");
				Blob image = rs.getBlob("image");
				BoardUserDao dao = new BoardUserDao();
				BoardUser b = dao.getUser(topicAuth);
				
				result = new Topic(name, topicDesc, b, flags, id, image);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	public boolean postTopic(Topic t) {
		PreparedStatement ps = null;
		boolean posted = false;
		
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "insert into topic (topicID, topicName, topicDesc, topicAuth) values (?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getId());
			ps.setString(2, t.getName());
			ps.setString(3, t.getDescription());
			ps.setString(4, t.getAuthor().getUsername());
			posted = !ps.execute();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return posted;
	}
	
	public void flagTopic(Topic t) {
		t.setFlags(t.getFlags() + 1);
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update topic set flags = ? where topicName = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getFlags());
			ps.setString(2, t.getName());
			ps.executeQuery();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void unflagTopic(Topic t) {
		t.setFlags(0);
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update topic set flags = ? where topicName = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getFlags());
			ps.setString(2, t.getName());
			ps.executeQuery();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void deleteTopic(Topic t) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "delete from reply where topicID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getId());
			ps.executeUpdate();
			
			sql = "delete from topic where topicID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getId());
			ps.executeUpdate();
			
			List<Topic> topics = getTopics();
			
			for (Topic topic: topics) {
				if (topic.getId() > t.getId()) {
					sql = "update topic set topicId = ? where topicId = ?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, topic.getId() - 1);
					ps.executeUpdate();
				}
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void postImage(Topic t, InputStream image) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "update topic set image = ? where topicID = ?";
			ps = conn.prepareStatement(sql);
			ps.setBlob(1, image);
			ps.setInt(2, t.getId());
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
