package my.projects.videorecommendations.domain;

import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.data.entities.UserEvent;
import my.projects.videorecommendations.data.entities.UserRating;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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

    public List<UserRating> byMovie(Movie movie) {
        return ratingsRepository.findAll(by(movie));
    }

    private Specification<UserRating> by(Movie movie) {
        return (root, query, cb) -> cb.equal(root.get("movieId"), movie.getId());
    }
}
