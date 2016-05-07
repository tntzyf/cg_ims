package com.cg.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

/**
 * Servlet Filter implementation class UserLoginFilter
 */
public class UserLoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public UserLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;  
        HttpServletResponse response = (HttpServletResponse) res;  
        
        String uri = request.getRequestURI();
        if(StringUtils.contains(uri, "/login.jsp") || StringUtils.contains(uri, "/UserAction")){
        	// pass the request along the filter chain
    		chain.doFilter(request, response);
    		return;
        }
        
        
        HttpSession session = request.getSession();  
        
        Object login = session.getAttribute("login");
        if(login == null || !(login instanceof Boolean)){
        	response.sendRedirect(request.getContextPath()+"/login/login.jsp");
        	return;
        }
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
