package my.projects.videorecommendations.domain;

import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.entities.UserEvent;

@Slf4j
public class MovieRatings {
    private final UserRatingsRepository ratingsRepository;

    public MovieRatings(UserRatingsRepository ratingsRepository) {
        this.ratingsRepository = ratingsRepository;
    }

    public void on(UserEvent event) {
        log.info("User event occurred: [{}]", event);
        new RatingPolicies().rate(event).ifPresent(x -> ratingsRepository.save(x));
    }

}
