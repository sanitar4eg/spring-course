package edu.learn.beans.services.discount;

import edu.learn.beans.models.User;

public interface DiscountStrategy {

	double calculateDiscount(User user);
}
