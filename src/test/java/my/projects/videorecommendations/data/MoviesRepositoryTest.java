package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.tests.PersistenceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@PersistenceTest
public class MoviesRepositoryTest {

    @Autowired
    private MoviesRepository repository;

    @Test
    void none() {
        assertThat(repository.findAll(null), empty());
    }

    @Test
    void saveOne() {
        repository.save(new Movie(
                "99",
                "Love Boat",
                Arrays.asList("Comedy", "Romance"),
                Arrays.asList()
        ));

        assertThat(repository.findAll(null), contains(allOf(
                hasProperty("id", is("99")),
                hasProperty("title", is("Love Boat")),
                hasProperty("genres", contains("Comedy", "Romance"))
        )));
    }
}