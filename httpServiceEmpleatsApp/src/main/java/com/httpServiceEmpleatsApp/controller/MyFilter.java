package com.httpServiceEmpleatsApp.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MyFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		System.out.println("Starting a transaction for req : " + req.getRequestURI());

		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("IT-Academy-Exercise", "Simple-Service");

		chain.doFilter(request, response);
	}
}