package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.BoardDao;
import com.revature.dao.ReplyDao;
import com.revature.model.Reply;
import com.revature.model.Topic;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String topicName = req.getHeader("topic");
		BoardDao dao = new BoardDao();
		Topic t = dao.getOneTopic(topicName);
		
		dao.unflagTopic(t);
		t.setFlags(0);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String topicName = req.getHeader("topic");
		BoardDao dao = new BoardDao();
		Topic t = dao.getOneTopic(topicName);
		
		dao.deleteTopic(t);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int topicID = Integer.parseInt(req.getHeader("topicID"));
		int replyID = Integer.parseInt(req.getHeader("replyID"));
		System.out.println(topicID + " " + replyID);
		ReplyDao dao = new ReplyDao();
		
		Reply r = dao.getReply(topicID, replyID);
		dao.delete(r);
	}

}
