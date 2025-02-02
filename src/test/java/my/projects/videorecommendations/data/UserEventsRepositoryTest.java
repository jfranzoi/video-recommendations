package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.MovieViewedEvent;
import my.projects.videorecommendations.tests.PersistenceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static my.projects.videorecommendations.data.TestData.STAR_WARS;
import static my.projects.videorecommendations.data.TestData.TOY_STORY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

@PersistenceTest
public class UserEventsRepositoryTest {

    @Autowired
    private UserEventsRepository repository;

    @Test
    void none() {
        assertThat(repository.findAll(null), empty());
    }

    @Test
    void saveMany_differentContext() {
        repository.save(new MovieViewedEvent("1", STAR_WARS, 99));
        repository.save(new MovieRatedEvent("1", TOY_STORY, 5));

        assertThat(repository.findAll(null), contains(
                new MovieViewedEvent("1", STAR_WARS, 99),
                new MovieRatedEvent("1", TOY_STORY, 5)
        ));
    }

    @Test
    void saveMany_sameContext_appended() {
        repository.save(new MovieViewedEvent("1", STAR_WARS, 20));
        repository.save(new MovieRatedEvent("1", STAR_WARS, 3));

        assertThat(repository.findAll(null), contains(
                new MovieViewedEvent("1", STAR_WARS, 20),
                new MovieRatedEvent("1", STAR_WARS, 3)
        ));
    }
}