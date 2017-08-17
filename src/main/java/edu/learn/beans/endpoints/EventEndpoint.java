package edu.learn.beans.endpoints;

import edu.learn.beans.configuration.WebServiceConfig;
import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.Rate;
import edu.learn.beans.services.AuditoriumService;
import edu.learn.beans.services.EventService;
import java.time.LocalDateTime;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EventEndpoint {

	private final EventService eventService;

	private final AuditoriumService auditoriumService;

	public EventEndpoint(EventService eventService, AuditoriumService auditoriumService) {
		this.eventService = eventService;
		this.auditoriumService = auditoriumService;
	}

	@PayloadRoot(namespace = WebServiceConfig.TARGET_NAMESPACE, localPart = "createEvent")
	@ResponsePayload
	public Event create() {
		Auditorium auditorium = auditoriumService.getAuditoriums().stream().findFirst().get();
		return eventService.create(new Event("name", Rate.HIGH, 100d, LocalDateTime.now(), auditorium));
	}
}
