package com.cg.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.cg.domain.JDSkuType;
import com.cg.source.ResourceUtils;

/**
 * Servlet implementation class UserAction
 */
public class UserAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("login".equalsIgnoreCase(method)){
			login(request, response);
		}
	}
	
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String usersStr = ResourceUtils.readResource("user");
		String[] users = StringUtils.split(usersStr,",");
		for(String userStr : users){
			String[] user = StringUtils.split(userStr,"=");
			String uName = user[0];
			String pWord = user[1];
			if(uName.equals(username) && pWord.equals(password)){
				request.getSession().setAttribute("login", true);
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				return;
			}
		}
		response.sendRedirect("/login/login.jsp");
	}
}
