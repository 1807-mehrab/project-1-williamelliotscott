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
import com.revature.model.BoardUser;
import com.revature.model.Topic;

@WebServlet("/RegulateServlet")
public class RegulateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Topic t = (Topic) session.getAttribute("topic");
		BoardDao dao = new BoardDao();
		dao.flagTopic(t);
		String page = (String) session.getAttribute("page");
		RequestDispatcher rd = req.getRequestDispatcher(page);
		rd.include(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		BoardUser user = (BoardUser) session.getAttribute("user");
		
		BoardDao dao = new BoardDao();
		List<Topic> flaggedTopics = dao.getFlaggedTopics();
		
		PrintWriter pw = resp.getWriter();
		PostServlet.listTopics(pw, flaggedTopics, user);
		pw.close();
	}

}
