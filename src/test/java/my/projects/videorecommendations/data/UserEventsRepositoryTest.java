package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.MovieViewedEvent;
import my.projects.videorecommendations.tests.PersistenceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

@PersistenceTest
public class UserEventsRepositoryTest {

    private static final String STAR_WARS = "1";
    private static final String TOY_STORY = "2";

    @Autowired
    private UserEventsRepository repository;

    @Test
    void none() {
        assertThat(repository.findAll(), empty());
    }

    @Test
    void saveMany_differentContext() {
        repository.save(new MovieViewedEvent("1", STAR_WARS, 99));
        repository.save(new MovieRatedEvent("1", TOY_STORY, 5));

        assertThat(repository.findAll(), contains(
                new MovieViewedEvent("1", STAR_WARS, 99),
                new MovieRatedEvent("1", TOY_STORY, 5)
        ));
    }

    @Test
    void saveMany_sameContext() {
        repository.save(new MovieViewedEvent("1", STAR_WARS, 20));
        repository.save(new MovieRatedEvent("1", STAR_WARS, 3));

        assertThat(repository.findAll(), contains(
                new MovieViewedEvent("1", STAR_WARS, 20),
                new MovieRatedEvent("1", STAR_WARS, 3)
        ));
    }
}