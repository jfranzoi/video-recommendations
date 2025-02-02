package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.UserRating;
import my.projects.videorecommendations.data.entities.UserRating.Type;
import my.projects.videorecommendations.tests.PersistenceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static my.projects.videorecommendations.data.TestData.STAR_WARS;
import static my.projects.videorecommendations.data.TestData.TOY_STORY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

@PersistenceTest
public class UserRatingPoliciesRepositoryTest {

    @Autowired
    private UserRatingsRepository repository;

    @Test
    void none() {
        assertThat(repository.findAll(null), empty());
    }

    @Test
    void saveMany_differentContext() {
        repository.save(new UserRating("1", STAR_WARS, 3, Type.EXPLICIT));
        repository.save(new UserRating("1", TOY_STORY, 5, Type.EXPLICIT));

        assertThat(repository.findAll(null), contains(
                new UserRating("1", STAR_WARS, 3, Type.EXPLICIT),
                new UserRating("1", TOY_STORY, 5, Type.EXPLICIT)
        ));
    }

    @Test
    void saveMany_sameContext_keepLast() {
        repository.save(new UserRating("1", STAR_WARS, 3, Type.EXPLICIT));
        repository.save(new UserRating("1", STAR_WARS, 5, Type.EXPLICIT));

        assertThat(repository.findAll(null), contains(
                new UserRating("1", STAR_WARS, 5, Type.EXPLICIT)
        ));
    }
}