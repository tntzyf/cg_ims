package com.cg.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cg.service.ErrorMessageService;

/**
 * Servlet implementation class ErrorMessageAction
 */
public class ErrorMessageAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErrorMessageAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request,response);
	}

	protected void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if("remove".equalsIgnoreCase(action)){
			remove(request,response);
		}
	}
	
	protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String messageId = request.getParameter("messageId");
		ErrorMessageService errorMessageService = ErrorMessageService.getInstance();
		errorMessageService.removeErrorMessage(messageId);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}
