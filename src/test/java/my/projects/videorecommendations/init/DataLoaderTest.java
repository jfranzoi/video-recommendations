package my.projects.videorecommendations.init;

import my.projects.videorecommendations.data.MovieRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.dummies.InMemoryMoviesRepository;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DataLoaderTest {

    @Test
    void movies_empty() throws Exception {
        MovieRepository repository = new InMemoryMoviesRepository();
        new DataLoader(repository, resourcesAt("data/empty")).process();

        assertThat(repository.findAll(), empty());
    }

    @Test
    void movies_full() throws Exception {
        MovieRepository repository = new InMemoryMoviesRepository();
        new DataLoader(repository, resourcesAt("data/full")).process();

        assertThat(repository.findAll(), hasSize(10));
    }

    @Test
    void movies_mapping() throws Exception {
        MovieRepository repository = new InMemoryMoviesRepository();
        new DataLoader(repository, resourcesAt("data/full")).process();

        // 1,Toy Story,Adventure|Animation|Children|Comedy|Fantasy
        assertThat(repository.findAll(), hasItem(Movie.builder()
                .id("1")
                .title("Toy Story")
                .genres(Arrays.asList("Adventure", "Animation", "Children", "Comedy", "Fantasy"))
                .build()
        ));
    }

    private Path resourcesAt(String location) throws Exception {
        return Path.of(ClassLoader.getSystemResource(location).toURI());
    }
}