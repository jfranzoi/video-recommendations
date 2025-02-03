package my.projects.videorecommendations.domain;

import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.entities.UserEvent;

@Slf4j
public class MovieRatings {
    private final UserRatingsRepository ratingsRepository;
    private final RatingPolicies ratingPolicies;

    public MovieRatings(UserRatingsRepository ratingsRepository) {
        this.ratingsRepository = ratingsRepository;
        this.ratingPolicies = new RatingPolicies();
    }

    public void on(UserEvent event) {
        log.info("User event occurred: [{}]", event);
        ratingPolicies.rate(event).ifPresent(x -> ratingsRepository.save(x));
    }

}
