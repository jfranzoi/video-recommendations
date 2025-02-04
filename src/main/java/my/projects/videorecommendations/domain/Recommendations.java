package my.projects.videorecommendations.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import my.projects.videorecommendations.data.MoviesRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.data.entities.UserRating;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

public class Recommendations {
    private final MoviesRepository moviesRepository;

    public Recommendations(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public List<Movie> byUser(String user) {
        List<Movie> rated = moviesRepository.findAll(ratedBy(user));
        return moviesRepository.findAll(
                otherThan(id(rated)).and(similarTo(rated))
        );
    }

    private Specification<Movie> similarTo(List<Movie> rated) {
        return belongingTo(genres(rated));
    }

    private List<String> genres(List<Movie> rated) {
        return rated.stream().map(x -> x.getGenres())
                .flatMap(Collection::stream)
                .toList();
    }

    private List<String> id(List<Movie> rated) {
        return rated.stream().map(x -> x.getId()).toList();
    }

    private Specification<Movie> otherThan(List<String> ids) {
        return (root, query, cb) -> cb.not(root.get("id").in(ids));
    }

    private Specification<Movie> belongingTo(List<String> genres) {
        return Specification.anyOf(genres.stream().map(x -> belongingTo(x)).toList());
    }

    private Specification<Movie> belongingTo(String genre) {
        return (root, query, cb) -> cb.isMember(genre, root.get("genres"));
    }

    private Specification<Movie> ratedBy(String user) {
        return (root, query, cb) -> root.get("id").in(moviesHighlyRatedBy(user, query, cb));
    }

    private Subquery<?> moviesHighlyRatedBy(String user, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Subquery<?> userRatings = query.subquery(String.class);
        Root<UserRating> root = userRatings.from(UserRating.class);
        userRatings.select(root.get("movieId"));
        userRatings.where(cb.and(
                root.get("userId").in(user),
                cb.greaterThanOrEqualTo(root.get("rating"), 4)
        ));
        return userRatings;
    }
}
