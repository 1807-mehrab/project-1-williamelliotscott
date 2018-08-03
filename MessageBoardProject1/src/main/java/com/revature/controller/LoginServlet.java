package com.revature.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.BoardUserDao;
import com.revature.model.BoardUser;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		System.out.println("logging in...");
		
		String name = req.getParameter("username");
		String pass = req.getParameter("password");
				
		if (BoardUserDao.validate(name, pass)) {
			HttpSession session = req.getSession();
			session.setAttribute("username", name);
			BoardUserDao dao = new BoardUserDao();
			BoardUser user = dao.getUser(name, pass);
			session.setAttribute("user", user);
			
			String page;
			if (user.isAdmin()) {
				page = "AdminPage.html";
			} else {
				page = "UserPage.html";
			}
			
			session.setAttribute("page", page);
			
			RequestDispatcher rd = req.getRequestDispatcher(page);
			rd.include(req, resp);
		} else {
			RequestDispatcher rd = req.getRequestDispatcher("MessageBoardFrontEnd.html");
			rd.include(req, resp);
			out.print("Your username or password is incorrect");
		}
		out.close();
	}
	
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		RequestDispatcher rd = req.getRequestDispatcher("MessageBoardFrontEnd.html");
		rd.include(req, resp);
		session.invalidate();
	}

}
