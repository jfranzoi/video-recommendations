package my.projects.videorecommendations.init;

import my.projects.videorecommendations.data.EventsRepository;
import my.projects.videorecommendations.data.MoviesRepository;
import my.projects.videorecommendations.data.UsersRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.dummies.InMemoryEventsRepository;
import my.projects.videorecommendations.dummies.InMemoryMoviesRepository;
import my.projects.videorecommendations.dummies.InMemoryUsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DataLoaderTest {

    private MoviesRepository movies;
    private UsersRepository users;
    private EventsRepository events;

    @BeforeEach
    void setUp() {
        movies = new InMemoryMoviesRepository();
        users = new InMemoryUsersRepository();
        events = new InMemoryEventsRepository();
    }

    @Test
    void movies_empty() throws Exception {
        new DataLoader(movies, users, events).process(resourcesAt("data/empty"));
        assertThat(movies.findAll(null), empty());
    }

    @Test
    void movies_full() throws Exception {
        new DataLoader(movies, users, events).process(resourcesAt("data/full"));
        assertThat(movies.findAll(null), hasSize(10));
    }

    @Test
    void movies_mapping() throws Exception {
        new DataLoader(movies, users, events).process(resourcesAt("data/full"));
        assertThat(movies.findAll(null), hasItem(Movie.builder()
                .id("1")
                .title("Toy Story")
                .genres(Arrays.asList("Adventure", "Animation", "Children", "Comedy", "Fantasy"))
                .build()
        ));
    }

    @Test
    void users_empty() throws Exception {
        new DataLoader(movies, users, events).process(resourcesAt("data/empty"));
        assertThat(users.findAll(), empty());
    }

    @Test
    void users_full() throws Exception {
        new DataLoader(movies, users, events).process(resourcesAt("data/full"));
        assertThat(users.findAll(), hasSize(3));
    }

    @Test
    void events_empty() throws Exception {
        new DataLoader(movies, users, events).process(resourcesAt("data/empty"));
        assertThat(events.findAll(), empty());
    }

    @Test
    void events_full() throws Exception {
        new DataLoader(movies, users, events).process(resourcesAt("data/full"));
        assertThat(events.findAll(), hasSize(6));
    }

    private Path resourcesAt(String location) throws Exception {
        return Path.of(ClassLoader.getSystemResource(location).toURI());
    }
}