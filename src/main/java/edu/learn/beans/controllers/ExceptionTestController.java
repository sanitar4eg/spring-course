package edu.learn.beans.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("test")
public class ExceptionTestController {

	@RequestMapping(method = RequestMethod.GET)
	public void test() {
		throw new RuntimeException("Test exception");
	}
}
