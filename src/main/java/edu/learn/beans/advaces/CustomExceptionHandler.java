package edu.learn.beans.advaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

	public static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	protected String handleException(Exception ex, Model model) {
		LOG.error("Redirect to error page", ex);
		model.addAttribute("message", ex.getMessage());
		return "error";
	}

}
