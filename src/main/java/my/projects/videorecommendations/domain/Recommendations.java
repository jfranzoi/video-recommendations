package my.projects.videorecommendations.domain;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import my.projects.videorecommendations.data.MoviesRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.data.entities.UserRating;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class Recommendations {
    private final MoviesRepository moviesRepository;

    public Recommendations(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public List<Movie> byUser(String user) {
        return moviesRepository.findAll(notRatedBy(user));
    }

    private Specification<Movie> notRatedBy(String user) {
        return (root, query, cb) -> root.get("id").in(moviesRatedBy(user, query)).not();
    }

    private Subquery<?> moviesRatedBy(String user, CriteriaQuery<?> query) {
        Subquery<?> subquery = query.subquery(String.class);
        Root<UserRating> userRatings = subquery.from(UserRating.class);
        subquery.select(userRatings.get("movieId"));
        subquery.where(userRatings.get("userId").in(user));
        return subquery;
    }
}
