package com.synergy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.synergy.connection.DbCon;
import com.synergy.dao.UserDao;
import com.synergy.model.User;


@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out=response.getWriter()){
		String email=request.getParameter("login-email");
		String password=request.getParameter("login-password");
		
		UserDao udao = new UserDao(DbCon.getConnection());
		User user = udao.userLogin(email, password);
		
		if(user != null) {
			request.getSession().setAttribute("auth", user);
			out.println("user loged in");
			response.sendRedirect("index.jsp");
		}else {
			out.println("user login failed");
		}
			
		
		}catch(ClassNotFoundException|SQLException e) {
			e.printStackTrace();
		}
	}

}
