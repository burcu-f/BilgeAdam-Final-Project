package com.techathome.web.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

	@ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
	public ModelAndView handleError404(HttpServletRequest request, HttpServletResponse response, Exception e) {
		log.error("Request: {} raised {}", request.getRequestURL(), e.getMessage());
		e.printStackTrace();
		return new ModelAndView("error/404");
	}
	
}