package my.projects.videorecommendations.init;

import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.MovieViewedEvent;
import my.projects.videorecommendations.data.entities.User;
import my.projects.videorecommendations.dummies.InMemoryMoviesRepository;
import my.projects.videorecommendations.dummies.InMemoryUserEventsRepository;
import my.projects.videorecommendations.dummies.InMemoryUserRatingsRepository;
import my.projects.videorecommendations.dummies.InMemoryUsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DataLoaderTest {

    private InMemoryMoviesRepository movies;
    private InMemoryUsersRepository users;
    private InMemoryUserEventsRepository events;
    private InMemoryUserRatingsRepository ratings;

    @BeforeEach
    void setUp() {
        movies = new InMemoryMoviesRepository();
        users = new InMemoryUsersRepository();
        events = new InMemoryUserEventsRepository();
        ratings = new InMemoryUserRatingsRepository();
    }

    @Test
    void movies_empty() throws Exception {
        new DataLoader(movies, users, events, ratings).process(resourcesAt("data/empty"));
        assertThat(movies.findAll(null), empty());
    }

    @Test
    void movies_full() throws Exception {
        new DataLoader(movies, users, events, ratings).process(resourcesAt("data/full"));
        assertThat(movies.findAll(null), hasSize(10));
    }

    @Test
    void movies_mapping() throws Exception {
        new DataLoader(movies, users, events, ratings).process(resourcesAt("data/full"));
        assertThat(movies.findAll(null), hasItem(new Movie(
                "1",
                "Toy Story",
                Arrays.asList("Adventure", "Animation", "Children", "Comedy", "Fantasy"),
                Arrays.asList()
        )));
    }

    @Test
    void users_empty() throws Exception {
        new DataLoader(movies, users, events, ratings).process(resourcesAt("data/empty"));
        assertThat(users.findAll(null), empty());
    }

    @Test
    void users_full() throws Exception {
        new DataLoader(movies, users, events, ratings).process(resourcesAt("data/full"));
        assertThat(users.findAll(null), hasSize(3));
    }

    @Test
    void users_mapping() throws Exception {
        new DataLoader(movies, users, events, ratings).process(resourcesAt("data/full"));
        assertThat(users.findAll(null), hasItem(new User("1", "Alice")));
    }

    @Test
    void events_empty() throws Exception {
        new DataLoader(movies, users, events, ratings).process(resourcesAt("data/empty"));
        assertThat(events.findAll(null), empty());
    }

    @Test
    void events_full() throws Exception {
        new DataLoader(movies, users, events, ratings).process(resourcesAt("data/full"));
        assertThat(events.findAll(null), hasSize(7));
    }

    @Test
    void events_manyFromSameRecord() throws Exception {
        new DataLoader(movies, users, events, ratings).process(resourcesAt("data/full"));
        assertThat(events.findAll(null), hasItems(
                new MovieRatedEvent("1", "1", 4),
                new MovieViewedEvent("1", "1", 85)
        ));
    }

    @Test
    void events_mapping() throws Exception {
        new DataLoader(movies, users, events, ratings).process(resourcesAt("data/full"));
        assertThat(events.findAll(null), hasItems(
                new MovieRatedEvent("1", "1", 4),
                new MovieRatedEvent("1", "2", 5),
                new MovieViewedEvent("1", "2", 90)
        ));
    }

    private Path resourcesAt(String location) throws Exception {
        return Path.of(ClassLoader.getSystemResource(location).toURI());
    }
}