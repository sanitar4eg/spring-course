package edu.learn.beans.controllers;

import edu.learn.beans.models.Event;
import edu.learn.beans.models.User;
import edu.learn.beans.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("discount")
public class DiscountController {

	private final DiscountService discountService;

	@Autowired
	public DiscountController(DiscountService discountService) {
		this.discountService = discountService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public double getDiscount(@RequestParam User user, @RequestParam Event event) {
		return discountService.getDiscount(user, event);
	}

}
