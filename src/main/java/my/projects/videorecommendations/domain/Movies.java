package my.projects.videorecommendations.domain;

import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.MoviesRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.web.entities.MovieFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

@Slf4j
public class Movies {
    private final MoviesRepository repository;

    public Movies(MoviesRepository repository) {
        this.repository = repository;
    }

    public List<Movie> all(MovieFilter filter) {
        log.info("Collecting movies, by: [{}]", filter);
        return repository.findAll(byGenre(filter));
    }

    private Specification<Movie> byGenre(MovieFilter filter) {
        return Optional.ofNullable(filter.getGenre())
                .map(x -> byGenre(x))
                .orElse(null);
    }

    private Specification<Movie> byGenre(String value) {
        return (root, query, cb) -> cb.isMember(value, root.get("genres"));
    }
}
