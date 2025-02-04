package my.projects.videorecommendations.domain;

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
                otherThan(id(rated))
                        .and(belongingTo(genres(rated)))
        );
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

    private Specification<Movie> belongingTo(List<String> preferredGenres) {
        return Specification.anyOf(preferredGenres.stream().map(x -> belongingTo(x)).toList());
    }

    private Specification<Movie> belongingTo(String genre) {
        return (root, query, cb) -> cb.isMember(genre, root.get("genres"));
    }

    private Specification<Movie> ratedBy(String user) {
        return (root, query, cb) -> root.get("id").in(moviesRatedBy(user, query));
    }

    private Subquery<?> moviesRatedBy(String user, CriteriaQuery<?> query) {
        Subquery<?> subquery = query.subquery(String.class);
        Root<UserRating> userRatings = subquery.from(UserRating.class);
        subquery.select(userRatings.get("movieId"));
        subquery.where(userRatings.get("userId").in(user));
        return subquery;
    }
}
