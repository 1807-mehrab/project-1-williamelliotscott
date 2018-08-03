package com.revature.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import com.revature.dao.BoardDao;
import com.revature.model.Topic;

@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Topic t = (Topic) session.getAttribute("topic");
		
		InputStream image = req.getInputStream();
		
		BoardDao dao = new BoardDao();
		dao.postImage(t, image);
		
		t = dao.getOneTopic(t.getId());
		PrintWriter pw = resp.getWriter();
		String page = (String) session.getAttribute("page");
		RequestDispatcher rd = req.getRequestDispatcher(page);
		rd.include(req, resp);
		pw.println("<img src = \"data:image/png;base64," + IOUtils.toString(image) + "\"/>");
	}

}
