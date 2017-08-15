package edu.learn.beans.services;

import edu.learn.beans.models.Event;
import edu.learn.beans.models.User;

public interface DiscountService {

	double getDiscount(User user, Event event);
}
