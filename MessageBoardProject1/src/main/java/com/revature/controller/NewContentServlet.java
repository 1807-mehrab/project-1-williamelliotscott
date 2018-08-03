package com.revature.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/NewContentServlet")
public class NewContentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String topicName = (String) req.getParameter("newTopicName");
		String topicDesc = (String) req.getParameter("description");
		HttpSession session = req.getSession();
		BoardUser user = (BoardUser) session.getAttribute("user");
		BoardDao dao = new BoardDao();
		List<Topic> topics = dao.getTopics();
		int id = topics.size() + 1;
				
		Topic newTopic = new Topic(topicName, topicDesc, user, 0, id);
				
		boolean posted = dao.postTopic(newTopic);
		
		PrintWriter pw = resp.getWriter();
		String page = (String) session.getAttribute("page");
		RequestDispatcher rd = req.getRequestDispatcher(page);
		rd.include(req, resp);
		if (!posted) {
			pw.println("Sorry, we could not post your topic.  The topic name may be already taken.  Please Try again.");
		} else {
			pw.println("Topic successfully posted!");
		}
		pw.close();
	}
	
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		BoardUser user = (BoardUser) session.getAttribute("user");
		Topic t = (Topic) session.getAttribute("topic");
		String replyText = req.getParameter("replyText");
		ReplyDao dao = new ReplyDao();
		List<Reply> replies = dao.getRepliesToTopic(t);
		int replyId = replies.size() + 1;
		
		Reply reply = new Reply(user, t, replyText, replyId);
		boolean posted = dao.postReply(t, reply);
		
		PrintWriter pw = resp.getWriter();
		String page = (String) session.getAttribute("page");
		RequestDispatcher rd = req.getRequestDispatcher(page);
		rd.include(req, resp);
		if (!posted) {
			pw.println("<button type = \"button\" onclick = \"loadTopicReplies('" + reply.getTopic().getName() + "')\">Unable to post reply.  Return to Topic</button>" 
					+ "<br/><br/>");
		} else {
			pw.println("<button type = \"button\" onclick = \"loadTopicReplies('" + reply.getTopic().getName() + "')\">Reply Successfully posted!  Return to Topic</button>" 
					+ "<br/><br/>");
		}
		pw.close();
		
	}
}
