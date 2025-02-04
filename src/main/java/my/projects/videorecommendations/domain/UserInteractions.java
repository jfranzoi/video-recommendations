package my.projects.videorecommendations.domain;

import my.projects.videorecommendations.data.UserEventsRepository;
import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.entities.UserEvent;

public class UserInteractions {
    private final UserEventsRepository userEventsRepository;
    private final UserRatingsRepository userRatingsRepository;

    public UserInteractions(UserEventsRepository userEventsRepository, UserRatingsRepository userRatingsRepository) {
        this.userEventsRepository = userEventsRepository;
        this.userRatingsRepository = userRatingsRepository;
    }

    public void on(UserEvent event) {
        new UserEvents(userEventsRepository).on(event);
        new MovieRatings(userRatingsRepository).on(event);
    }
}
