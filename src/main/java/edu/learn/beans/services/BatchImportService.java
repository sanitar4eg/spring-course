package edu.learn.beans.services;

import edu.learn.beans.models.Event;
import edu.learn.beans.models.User;
import java.util.List;

public interface BatchImportService {

	void importEvents(List<Event> events);

	void importUsers(List<User> events);

}
