package my.projects.videorecommendations.domain;

import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.entities.UserEvent;
import my.projects.videorecommendations.data.entities.UserRating;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

@Slf4j
public class MovieRatings {
    private final UserRatingsRepository ratingsRepository;
    private final RatingPolicies ratingPolicies;

    public MovieRatings(UserRatingsRepository ratingsRepository) {
        this.ratingsRepository = ratingsRepository;
        this.ratingPolicies = new RatingPolicies();
    }

    public void on(UserEvent event) {
        Optional<UserRating> existing = ratingsRepository.findOne(byUser(event).and(byMovie(event)));
        ratingPolicies.rate(event).ifPresent(occurred -> applyTo(existing, occurred));
    }

    private void applyTo(Optional<UserRating> existing, UserRating occurred) {
        ratingsRepository.save(combine(occurred, existing));
    }

    private UserRating combine(UserRating occurred, Optional<UserRating> existing) {
        return ratingPolicies.combine(existing, occurred);
    }

    private Specification<UserRating> byUser(UserEvent event) {
        return (root, query, cb) -> cb.equal(root.get("userId"), event.getUserId());
    }

    private Specification<UserRating> byMovie(UserEvent event) {
        return (root, query, cb) -> cb.equal(root.get("movieId"), event.getMovieId());
    }
}
