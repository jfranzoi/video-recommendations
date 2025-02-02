package my.projects.videorecommendations.domain;

import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.MovieViewedEvent;
import my.projects.videorecommendations.data.entities.UserRating;
import my.projects.videorecommendations.dummies.InMemoryUserEventsRepository;
import my.projects.videorecommendations.dummies.InMemoryUserRatingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

public class UserEventsTest {

    private InMemoryUserEventsRepository events;
    private InMemoryUserRatingsRepository ratings;

    @BeforeEach
    void setUp() {
        events = new InMemoryUserEventsRepository();
        ratings = new InMemoryUserRatingsRepository();
    }

    @Test
    void ratings_explicit() {
        new UserEvents(events, ratings).on(new MovieRatedEvent("1", "1", 5));
        assertThat(ratings.findAll(), contains(
                new UserRating("1", "1", 5)
        ));
    }

    @Test
    void ratings_implicit() {
        new UserEvents(events, ratings).on(new MovieViewedEvent("1", "1", 100));
        assertThat(ratings.findAll(), contains(
                new UserRating("1", "1", 5)
        ));
    }

    @Test
    void ratings_none() {
        new UserEvents(events, ratings).on(new MovieViewedEvent("1", "1", 0));
        assertThat(ratings.findAll(), empty());
    }
}