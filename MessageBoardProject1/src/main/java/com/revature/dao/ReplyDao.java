package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.BoardUser;
import com.revature.model.Reply;
import com.revature.model.Topic;
import com.revature.util.ConnectionUtil;

public class ReplyDao {
	
	public List<Reply> getRepliesToTopic(Topic t) {
		List<Reply> replies = new ArrayList<>();
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from reply where topicId=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				BoardUserDao dao = new BoardUserDao();
				BoardUser user = dao.getUser(rs.getString("userAuth"));
				String text = rs.getString("replyText");
				int id = rs.getInt("replyID");
				Reply r = new Reply(user, t, text, id);
				replies.add(r);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return replies;
	}
	
	public List<Reply> getRepliesFromUser(BoardUser b) {
		List<Reply> replies = new ArrayList<>();
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from reply where userAuth=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, b.getUsername());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				BoardUserDao dao = new BoardUserDao();
				BoardUser user = dao.getUser(rs.getString("userAuth"));
				BoardDao daoB = new BoardDao();
				Topic t = daoB.getOneTopic(rs.getInt("topicID"));
				String text = rs.getString("replyText");
				int id = rs.getInt("replyID");
				Reply r = new Reply(user, t, text, id);
				replies.add(r);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return replies;
	}
	
	public boolean postReply(Topic t, Reply r) {
		boolean posted = false;
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "insert into reply (topicID, replyID, userAuth, replyText) values (?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getId());
			ps.setInt(2, r.getId());
			ps.setString(3, r.getAuthor().getUsername());
			ps.setString(4, r.getText());
			posted = !ps.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return posted;
	}
	
	public Reply getReply(int topicID, int replyID) {
		Reply reply = null;
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from reply where topicID = ? and replyID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, topicID);
			ps.setInt(2, replyID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			BoardUserDao dao = new BoardUserDao();
			BoardUser auth = dao.getUser(rs.getString("userAuth"));
			
			BoardDao tDao = new BoardDao();
			Topic t = tDao.getOneTopic(topicID);
			
			reply = new Reply(auth, t, rs.getString("replyText"), replyID);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return reply;
	}

	public void delete(Reply r) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "delete from reply where topicID = ? and replyID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, r.getTopic().getId());
			ps.setInt(2, r.getId());
			ps.executeUpdate();
			
			List<Reply> replies = getRepliesToTopic(r.getTopic());
			for (Reply reply: replies) {
				if (reply.getId() > r.getId()) {
					sql = "update reply set replyID = ? where replyID = ?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, reply.getId());
					ps.setInt(2, reply.getId() - 1);
					ps.executeUpdate();
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
