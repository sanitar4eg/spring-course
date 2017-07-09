package edu.learn.beans.daos.mocks;

import edu.learn.beans.daos.db.EventDAOImpl;
import edu.learn.beans.models.Event;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 06/2/16 Time: 2:41 PM
 */
public class EventDAOMock extends EventDAOImpl {

	public static final Logger LOG = LoggerFactory.getLogger(EventDAOMock.class);

	private final List<Event> events;

	public EventDAOMock(List<Event> events) {
		this.events = events;
	}

	public void init() {
		cleanup();
		events.forEach(this::create);
	}

	public void cleanup() {
		LOG.info("deleting ");
		LOG.info(getAll().toString());
		getAll().forEach(this::delete);
	}
}
