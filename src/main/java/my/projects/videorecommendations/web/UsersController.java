package my.projects.videorecommendations.web;

import my.projects.videorecommendations.data.UserEventsRepository;
import my.projects.videorecommendations.data.UsersRepository;
import my.projects.videorecommendations.data.entities.User;
import my.projects.videorecommendations.data.entities.UserEvent;
import my.projects.videorecommendations.web.entities.UserEventReference;
import my.projects.videorecommendations.web.entities.UserHistory;
import my.projects.videorecommendations.web.entities.UserReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserEventsRepository userEventsRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> byId(@PathVariable String id) {
        return usersRepository.findBy(id)
                .map(x -> ResponseEntity.ok().body(toHistory(x)))
                .orElse(ResponseEntity.notFound().build());
    }

    private UserHistory toHistory(User user) {
        List<UserEvent> events = userEventsRepository.findByUserId(user.getId());
        return new UserHistory(
                toUserReference(user),
                events.stream().map(x -> toUserEventReference(x)).toList()
        );
    }

    private UserReference toUserReference(User user) {
        return new UserReference(user.getId(), user.getName());
    }

    private UserEventReference toUserEventReference(UserEvent event) {
        return new UserEventReference(
                toEventType(event),
                event.getMovieId()
        );
    }

    private String toEventType(UserEvent event) {
        return event.getClass().getSimpleName()
                .replace("Movie", "")
                .replace("Event", "")
                .toLowerCase();
    }
}
