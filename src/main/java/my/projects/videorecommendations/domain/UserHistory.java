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
public class UserHistory {
    private final UserEventsRepository userEventsRepository;

    public UserHistory(UserEventsRepository userEventsRepository) {
        this.userEventsRepository = userEventsRepository;
    }

    public List<UserEvent> by(User user, UserHistoryFilter filter) {
        log.info("Collecting user events, by: [{}]", filter);
        return userEventsRepository.findAll(byUser(user).and(byFilter(filter)));
    }

    public Specification<UserEvent> byUser(User user) {
        return (root, query, cb) -> cb.equal(root.get("userId"), user.getId());
    }

    private Specification<UserEvent> byFilter(UserHistoryFilter filter) {
        return Optional.ofNullable(filter.getType())
                .map(x -> byFilter(x))
                .orElse(null);
    }

    private Specification<UserEvent> byFilter(String value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), value);
    }
}
