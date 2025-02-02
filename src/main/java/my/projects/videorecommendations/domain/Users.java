package my.projects.videorecommendations.domain;

import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.UserEventsRepository;
import my.projects.videorecommendations.data.UsersRepository;
import my.projects.videorecommendations.data.entities.User;
import my.projects.videorecommendations.data.entities.UserEvent;
import my.projects.videorecommendations.web.entities.UserHistoryFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

@Slf4j
public class Users {
    private final UsersRepository usersRepository;
    private final UserEventsRepository userEventsRepository;

    public Users(UsersRepository usersRepository, UserEventsRepository userEventsRepository) {
        this.usersRepository = usersRepository;
        this.userEventsRepository = userEventsRepository;
    }

    public Optional<User> by(String id) {
        log.info("Collecting user, by: [{}]", id);
        return usersRepository.findOne(userById(id));
    }

    public List<UserEvent> historyBy(User user, UserHistoryFilter filter) {
        log.info("Collecting user events, by: [{}]", filter);
        return userEventsRepository.findAll(
                eventsByUser(user).and(eventsByFilter(filter))
        );
    }

    private Specification<User> userById(String id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }

    private Specification<UserEvent> eventsByUser(User user) {
        return (root, query, cb) -> cb.equal(root.get("userId"), user.getId());
    }

    private Specification<UserEvent> eventsByFilter(UserHistoryFilter filter) {
        return Optional.ofNullable(filter.getType())
                .map(x -> eventsByFilter(x))
                .orElse(null);
    }

    private Specification<UserEvent> eventsByFilter(String value) {
        return (root, query, cb) -> cb.equal(root.get("type"), value);
    }
}
