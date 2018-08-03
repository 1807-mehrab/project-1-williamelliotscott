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

import com.revature.dao.BoardUserDao;
import com.revature.model.BoardUser;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		BoardUser user = (BoardUser) session.getAttribute("user");
		
		PrintWriter pw = resp.getWriter();
		pw.println("<h3>" + user.getUsername() + "</h3>"
				+ user.getUserdesc() + "<br/><br/>");
		
		pw.println("<form action = \"ProfileServlet\" method = \"get\">"
				+ "Update About Me: <br/><textarea name = \"newAboutMe\"></textarea><br/>"
				+ "<input type = \"submit\" value = \"Submit new About Me\">"
				+ "</form>");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		BoardUser user = (BoardUser) session.getAttribute("user");
		String newAboutMe = req.getParameter("newAboutMe");
		
		user.setUserdesc(newAboutMe);
		
		BoardUserDao dao = new BoardUserDao();
		dao.updateUser(user);
		
		String page = (String) session.getAttribute("page");
		RequestDispatcher rd = req.getRequestDispatcher(page);
		rd.include(req, resp);
		PrintWriter pw = resp.getWriter();
		pw.println("Profile Updated");
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardUserDao dao = new BoardUserDao();
		List<BoardUser> users = dao.getAllUsers();
		PrintWriter pw = resp.getWriter();
		
		System.out.println("Servlet successfully accessed");
		
		for (BoardUser b: users) {
			pw.println("Username: " + b.getUsername() + "<br/>"
					+ "About this User: " + b.getUserdesc() + "<br/>");
			
			String isAdmin;
			if (b.isAdmin()) isAdmin = "yes";
			else isAdmin = "no";
			
			pw.println("Administrator: " + isAdmin + "<br/><br/>");
		}
		
		pw.close();
	}

}
