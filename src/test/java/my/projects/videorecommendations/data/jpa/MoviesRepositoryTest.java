package my.projects.videorecommendations.data.jpa;

import my.projects.videorecommendations.data.MoviesRepository;
import my.projects.videorecommendations.data.entities.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(properties = {
        "application.data.folder=src/test/resources/data/empty"
})
@ActiveProfiles({"test"})
public class MoviesRepositoryTest {

    @Autowired
    private MoviesRepository repository;

    @Test
    void none() {
        assertThat(repository.findAll(), empty());
    }

    @Test
    void saveOne() {
        repository.save(Movie.builder()
                .id("99").title("Love Boat").genres(Arrays.asList("Comedy", "Romance"))
                .build()
        );

        assertThat(repository.findAll(), contains(allOf(
                hasProperty("id", is("99")),
                hasProperty("title", is("Love Boat")),
                hasProperty("genres", contains("Comedy", "Romance"))
        )));
    }
}