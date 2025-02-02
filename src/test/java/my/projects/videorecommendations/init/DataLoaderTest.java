package my.projects.videorecommendations.init;

import my.projects.videorecommendations.data.MovieRepository;
import my.projects.videorecommendations.data.UserRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.dummies.InMemoryMoviesRepository;
import my.projects.videorecommendations.dummies.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DataLoaderTest {

    private MovieRepository movies;
    private UserRepository users;

    @BeforeEach
    void setUp() {
        movies = new InMemoryMoviesRepository();
        users = new InMemoryUserRepository();
    }

    @Test
    void movies_empty() throws Exception {
        new DataLoader(movies, users).process(resourcesAt("data/empty"));
        assertThat(movies.findAll(), empty());
    }

    @Test
    void movies_full() throws Exception {
        new DataLoader(movies, users).process(resourcesAt("data/full"));
        assertThat(movies.findAll(), hasSize(10));
    }

    @Test
    void movies_mapping() throws Exception {
        new DataLoader(movies, users).process(resourcesAt("data/full"));
        assertThat(movies.findAll(), hasItem(Movie.builder()
                .id("1")
                .title("Toy Story")
                .genres(Arrays.asList("Adventure", "Animation", "Children", "Comedy", "Fantasy"))
                .build()
        ));
    }

    @Test
    void users_empty() throws Exception {
        new DataLoader(movies, users).process(resourcesAt("data/empty"));
        assertThat(users.findAll(), empty());
    }

    @Test
    void users_full() throws Exception {
        new DataLoader(movies, users).process(resourcesAt("data/full"));
        assertThat(users.findAll(), hasSize(3));
    }

    private Path resourcesAt(String location) throws Exception {
        return Path.of(ClassLoader.getSystemResource(location).toURI());
    }
}