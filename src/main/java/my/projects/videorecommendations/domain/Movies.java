package my.projects.videorecommendations.domain;

import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.MovieRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.web.entities.MovieFilter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.util.Predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class Movies {
    private final MovieRepository repository;

    public Movies(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> all(MovieFilter filter) {
        log.info("Collecting movies, by: [{}]", filter);
        return repository.findAll().stream()
                .filter(byGenre(filter))
                .collect(Collectors.toList());
    }

    private Predicate<Movie> byGenre(MovieFilter genre) {
        if (Strings.isNotBlank(genre.getGenre())) {
            return (Movie movie) -> movie.getGenres().contains(genre.getGenre());
        }

        return Predicates.isTrue();
    }
}
