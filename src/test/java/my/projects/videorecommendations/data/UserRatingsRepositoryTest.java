package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.UserRating;
import my.projects.videorecommendations.tests.PersistenceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static my.projects.videorecommendations.data.TestData.STAR_WARS;
import static my.projects.videorecommendations.data.TestData.TOY_STORY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

@PersistenceTest
public class UserRatingsRepositoryTest {

    @Autowired
    private UserRatingsRepository repository;

    @Test
    void none() {
        assertThat(repository.findAll(), empty());
    }

    @Test
    void saveMany_differentContext() {
        repository.save(new UserRating("1", STAR_WARS, 3, "explicit"));
        repository.save(new UserRating("1", TOY_STORY, 5, "explicit"));

        assertThat(repository.findAll(), contains(
                new UserRating("1", STAR_WARS, 3, "explicit"),
                new UserRating("1", TOY_STORY, 5, "explicit")
        ));
    }

    @Test
    void saveMany_sameContext_keepLast() {
        repository.save(new UserRating("1", STAR_WARS, 3, "explicit"));
        repository.save(new UserRating("1", STAR_WARS, 5, "explicit"));

        assertThat(repository.findAll(), contains(
                new UserRating("1", STAR_WARS, 5, "explicit")
        ));
    }
}