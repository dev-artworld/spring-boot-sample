package com.equinix.appops.dart.portal.web.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.equinix.appops.dart.portal.model.domain.User;
import com.equinix.appops.dart.portal.model.domain.UserThreadLocal;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class ThreadLocalFilter implements Filter {
	
	private static final Logger log=Logger.getLogger("threadLocalFilterLogger");
	public ThreadLocalFilter() {
	}
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
//		String requestURL = httpServletRequest.getRequestURI();
//		if(null!=requestURL && (!requestURL.contains("AppOpsDartPortal")||!requestURL.contains("10.193.164.145:8080"))){
		
		Object userobj = httpServletRequest.getSession().getAttribute("user");
		UserThreadLocal usrThreadLocal=new UserThreadLocal((User)userobj);
		if(null==userobj && httpServletRequest!=null && httpServletRequest.getRequestURI()!=null 
				&& !httpServletRequest.getRequestURI().equals("/AppOpsDartPortalMS/index.html") &&
					!httpServletRequest.getRequestURI().contains("/AppOpsDartPortalMS/unauthorized.html") &&
					!httpServletRequest.getRequestURI().contains("/AppOpsDartPortalMS/logout.html") &&
					 !httpServletRequest.getRequestURI().contains("/AppOpsDartPortalMS/dashboard") &&
					(httpServletRequest.getRequestURI().contains("html") ||
					 httpServletRequest.getRequestURI().equals("/AppOpsDartPortalMS/"))){
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect("/AppOpsDartPortalMS/index.html");
			return;
		}else{
			chain.doFilter(request, response);
		}
		usrThreadLocal.removeUser();
//	}
	}

	public void init(FilterConfig fConfig) throws ServletException {				
	}

}
