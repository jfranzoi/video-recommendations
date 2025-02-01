package my.projects.videorecommendations.data;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class DataLoaderTest {

    @Test
    void movies_empty() throws Exception {
        MovieRepository repository = new InMemoryMoviesRepository();
        new DataLoader(repository, resourcesAt("data/empty")).process();

        assertThat(repository.all(), hasSize(0));
    }

    @Test
    void movies_full() throws Exception {
        MovieRepository repository = new InMemoryMoviesRepository();
        new DataLoader(repository, resourcesAt("data/full")).process();

        assertThat(repository.all(), hasSize(10));
    }

    private Path resourcesAt(String location) throws Exception {
        return Path.of(ClassLoader.getSystemResource(location).toURI());
    }
}