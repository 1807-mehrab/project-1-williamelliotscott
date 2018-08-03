package com.revature.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.BoardDao;
import com.revature.dao.ReplyDao;
import com.revature.model.BoardUser;
import com.revature.model.Reply;
import com.revature.model.Topic;

@WebServlet("/ReplyServlet")
public class ReplyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String topicName = req.getHeader("topic");
		BoardDao daoTopic = new BoardDao();
		Topic t = daoTopic.getOneTopic(topicName);
		
		HttpSession session = req.getSession();
		session.setAttribute("topic", t);
		BoardUser user = (BoardUser) session.getAttribute("user");
		
		ReplyDao daoReply = new ReplyDao();
		List<Reply> replies = daoReply.getRepliesToTopic(t);
		PrintWriter pw = resp.getWriter();
		
		pw.println("<h1>" + t.getName() + "</h1>");
		pw.println("<h3>" + t.getDescription() + "</h3>");
		pw.println("Posted by " + t.getAuthor().getUsername() + "<br/>");
		pw.println("Flags: " + t.getFlags() + "<br/>");
		if (t.getImage() != null) {
			pw.println("<img src = \"data:image/png;base64," + t.getImage() + "\"/>");
		}
		pw.println("<form action = \"RegulateServlet\" method = \"get\">"
				+ "<button name = \"topic\" value = \"" + t.getName()+ "\">Flag</button>" 
				+ "</form>");
		
		if (user.getUsername().equals(t.getAuthor().getUsername())) {
			pw.println("<form action = \"ImageServlet\" method = \"post\" enctype = \"multipart/form-data\">"
					+ "Select an image to add to this topic: <br/><input type = \"file\" name = \"image\" /><br/>"
					+ "<input type = \"submit\" name = \"upload\" />"
					+ "</form><br/><br/>");
		}
		
		for (Reply r: replies) {
			pw.println(r.getAuthor().getUsername() + " said: " + r.getText() + "<br/>");
			if (user.isAdmin()) pw.println("<button type = \"button\" onclick = \"deleteReply(" + t.getId() + ", " + r.getId() + ")\">Delete</button>");
			pw.println("<br/>");
		}
		
		pw.println("<form action = \"NewContentServlet\" method = \"get\">"
				+ "Reply: <br/><textarea name = \"replyText\"></textarea>"
				+ "<input type = \"submit\" value = \"Submit Reply\">"
				+ "</form>"
				);
		
		pw.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		BoardUser user = (BoardUser) session.getAttribute("user");
		
		ReplyDao dao = new ReplyDao();
		List<Reply> replies = dao.getRepliesFromUser(user);
		PrintWriter pw = resp.getWriter();
		
		for (Reply r: replies) {
			pw.println(r.getAuthor().getUsername() + " said: " + r.getText() + "<br/>");
			
			pw.println("<button type = \"button\" onclick = \"loadTopicReplies('" + r.getTopic().getName() + "')\">View Post</button>" 
					+ "<br/><br/>");
		}
		
		pw.close();
	}
	
}
