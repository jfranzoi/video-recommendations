package my.projects.videorecommendations.web;

import my.projects.videorecommendations.data.UserEventsRepository;
import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.UsersRepository;
import my.projects.videorecommendations.data.entities.User;
import my.projects.videorecommendations.data.entities.UserEvent;
import my.projects.videorecommendations.domain.UserEvents;
import my.projects.videorecommendations.domain.Users;
import my.projects.videorecommendations.web.entities.UserEventReference;
import my.projects.videorecommendations.web.entities.UserHistoryDetails;
import my.projects.videorecommendations.web.entities.UserHistoryFilter;
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
public class UsersHistoryController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserEventsRepository userEventsRepository;

    @Autowired
    private UserRatingsRepository userRatingsRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> byId(@PathVariable String id, UserHistoryFilter filter) {
        return new Users(usersRepository).by(id)
                .map(x -> ResponseEntity.ok().body(toHistoryDetails(x, filter)))
                .orElse(ResponseEntity.notFound().build());
    }

    private UserHistoryDetails toHistoryDetails(User user, UserHistoryFilter filter) {
        List<UserEvent> events = new UserEvents(userEventsRepository).historyBy(user, filter);
        return new UserHistoryDetails(
                toUserReference(user),
                events.stream().map(x -> toUserEventReference(x)).toList()
        );
    }

    private UserReference toUserReference(User user) {
        return new UserReference(user.getId(), user.getName());
    }

    private UserEventReference toUserEventReference(UserEvent event) {
        return new UserEventReference(
                event.getType(),
                event.getMovieId()
        );
    }

}
