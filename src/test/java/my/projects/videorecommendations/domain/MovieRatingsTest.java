package my.projects.videorecommendations.domain;

import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.MovieViewedEvent;
import my.projects.videorecommendations.data.entities.UserRating;
import my.projects.videorecommendations.data.entities.UserRating.Type;
import my.projects.videorecommendations.dummies.InMemoryUserRatingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

public class MovieRatingsTest {

    private InMemoryUserRatingsRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRatingsRepository();
    }

    @Test
    void ratings_explicit() {
        new MovieRatings(repository).on(new MovieRatedEvent("1", "1", 5));
        assertThat(repository.findAll(null), contains(
                new UserRating("1", "1", 5, Type.EXPLICIT)
        ));
    }

    @Test
    void ratings_implicit() {
        new MovieRatings(repository).on(new MovieViewedEvent("1", "1", 100));
        assertThat(repository.findAll(null), contains(
                new UserRating("1", "1", 5, Type.IMPLICIT)
        ));
    }

    @Test
    void ratings_none() {
        new MovieRatings(repository).on(new MovieViewedEvent("1", "1", 0));
        assertThat(repository.findAll(null), empty());
    }
}