package my.projects.videorecommendations.domain;

import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.UserEventsRepository;
import my.projects.videorecommendations.data.entities.User;
import my.projects.videorecommendations.data.entities.UserEvent;
import my.projects.videorecommendations.web.entities.UserHistoryFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

@Slf4j
public class UserEvents {
    private final UserEventsRepository userEventsRepository;

    public UserEvents(UserEventsRepository userEventsRepository) {
        this.userEventsRepository = userEventsRepository;
    }

    public List<UserEvent> historyBy(User user, UserHistoryFilter filter) {
        log.info("Collecting user events, by: [{}]", filter);
        return userEventsRepository.findAll(
                byUser(user).and(byFilter(filter))
        );
    }

    public void on(UserEvent event) {
        log.info("User event occurred: [{}]", event);
        userEventsRepository.save(event);
    }

    private Specification<UserEvent> byUser(User user) {
        return (root, query, cb) -> cb.equal(root.get("userId"), user.getId());
    }

    private Specification<UserEvent> byFilter(UserHistoryFilter filter) {
        return Optional.ofNullable(filter.getType())
                .map(x -> byFilter(x))
                .orElse(null);
    }

    private Specification<UserEvent> byFilter(String type) {
        return (root, query, cb) -> cb.equal(root.get("type"), type);
    }
}
