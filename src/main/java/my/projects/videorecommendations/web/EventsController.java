package my.projects.videorecommendations.web;

import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.UserEventsRepository;
import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.UserEvent;
import my.projects.videorecommendations.domain.MovieRatings;
import my.projects.videorecommendations.domain.UserEvents;
import my.projects.videorecommendations.web.entities.EventDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/events")
@Slf4j
public class EventsController {

    @Autowired
    private UserEventsRepository userEventsRepository;

    @Autowired
    private UserRatingsRepository userRatingsRepository;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> ingest(@RequestBody EventDetails details) {
        log.info("Ingesting event, details: [{}]", details);
        toEvent(details).ifPresent(x -> {
            new UserEvents(userEventsRepository).on(x);
            new MovieRatings(userRatingsRepository).on(x);
        });

        return ResponseEntity.accepted().build();
    }

    private Optional<UserEvent> toEvent(EventDetails details) {
        if ("rated".equals(details.getType())) {
            return Optional.of(toMovieRated(details));
        }

        return Optional.empty();
    }

    private MovieRatedEvent toMovieRated(EventDetails details) {
        return new MovieRatedEvent(details.getUser(), details.getMovie(), Integer.parseInt(details.getValue()));
    }
}
