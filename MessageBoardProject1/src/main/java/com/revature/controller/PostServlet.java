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
import com.revature.model.BoardUser;
import com.revature.model.Topic;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		BoardUser user = (BoardUser) session.getAttribute("user");
		
		
		BoardDao dao = new BoardDao();
		List<Topic> topics = dao.getTopics(user);
		
		
		PrintWriter pw = resp.getWriter();
		listTopics(pw, topics, user);
		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		BoardUser user = (BoardUser) session.getAttribute("user");
		
		BoardDao dao = new BoardDao();
		List<Topic> topics = dao.getTopics();
		
		PrintWriter pw = resp.getWriter();
	
		listTopics(pw, topics, user);
		pw.close();
	}
	
	public static void listTopics(PrintWriter pw, List<Topic> topics, BoardUser user) {
		for (Topic t: topics) {
			pw.println("Topic: " + t.getName() + "<br/>");
			pw.println("Author: " + t.getAuthor().getUsername() + "<br/>");
			pw.println("Description: " + t.getDescription() + "<br/>");
			pw.println("Flags: " + t.getFlags() + "<br/>");
			//check
			pw.println("<button type = \"button\" onclick = \"loadTopicReplies('" + t.getName() + "')\">View Replies</button>");
			
			if (user.isAdmin()) {
				pw.println("<button type = \"button\" onclick = \"deleteTopic('" + t.getName() + "')\">Delete</button>");
				pw.println("<button type = \"button\" onclick = \"deleteFlags('" + t.getName() + "')\">Unflag</button>");
			}
			pw.println("<br/><br/>");
		}
	}
}
