package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.MovieViewedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

@SpringBootTest(properties = {
        "application.data.folder=src/test/resources/data/empty"
})
@ActiveProfiles({"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserEventsRepositoryTest {

    @Autowired
    private UserEventsRepository repository;

    @Test
    void none() {
        assertThat(repository.findAll(), empty());
    }

    @Test
    void saveMany() {
        repository.save(new MovieViewedEvent("1", "1", 99));
        repository.save(new MovieRatedEvent("1", "2", 5));

        assertThat(repository.findAll(), contains(
                new MovieViewedEvent("1", "1", 99),
                new MovieRatedEvent("1", "2", 5)
        ));
    }
}