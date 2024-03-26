package com.techathome.web.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

	@ExceptionHandler(Throwable.class)
	public Object handleError500(HttpServletRequest request, HttpServletResponse response, Exception e) {
		log.error("Request: {} raised {}", request.getRequestURL(), e.getMessage());
		e.printStackTrace();
		return !response.isCommitted() ? response : null;
	}

	@ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
	public ModelAndView handleError404(HttpServletRequest request, HttpServletResponse response, Exception e) {
		log.error("Request: {} raised {}", request.getRequestURL(), e.getMessage());
		e.printStackTrace();
		return new ModelAndView("error/404");
	}
	
//	@ExceptionHandler(NoResourceFoundException.class)
//	public Object handleError404(HttpServletRequest request, HttpServletResponse response, Exception e) {
//		log.error("Request: {} raised {}", request.getRequestURL(), e.getMessage());
//		e.printStackTrace();
//		return !response.isCommitted() ? response : null;
//	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bozuk JSON nesnesi")
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public void handleException(HttpServletRequest request, Exception e) {
		log.error("Request: {} raised {}", request.getRequestURL(), e.getMessage());
		e.printStackTrace();
	}

	
//	@ExceptionHandler(BozaRuntimeException.class)
//	public ResponseEntity<RestResponseBody> handleBozaRuntimeException(
//			HttpServletRequest request,
//			final BozaRuntimeException e) {
//		String errMsg = String.format("Request: %s raised %s", request.getRequestURL(), e.getMessage());
//		log.error(errMsg, e);
//		RestResponseBody result = new RestResponseBody();
//		// TODO messageresolver'dan code'la çek, yoksa e.getMessage kullan
//		result.setMessage(e.getMessage());
//		result.setCode(e.errorCode());
//		return ResponseEntity.badRequest().body(result);
//	}
}