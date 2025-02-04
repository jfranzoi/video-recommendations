package my.projects.videorecommendations.data.entities;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MovieTest {

    @Test
    void rating_none() {
        List<UserRating> ratings = Arrays.asList();
        assertThat(movieWith(ratings).rating(), is(Optional.empty()));
    }

    @Test
    void rating_single() {
        List<UserRating> ratings = Arrays.asList(
                new UserRating("1", "99", 5, UserRating.Type.EXPLICIT)
        );

        assertThat(movieWith(ratings).rating(), is(Optional.of(5)));
    }

    @Test
    void rating_many_asAverage() {
        List<UserRating> ratings = Arrays.asList(
                new UserRating("1", "99", 5, UserRating.Type.EXPLICIT),
                new UserRating("1", "99", 1, UserRating.Type.EXPLICIT)
        );

        assertThat(movieWith(ratings).rating(), is(Optional.of(3)));
    }

    @Test
    void rating_many_asAverage_floor() {
        List<UserRating> ratings = Arrays.asList(
                new UserRating("1", "99", 5, UserRating.Type.EXPLICIT),
                new UserRating("1", "99", 2, UserRating.Type.EXPLICIT)
        );

        assertThat(movieWith(ratings).rating(), is(Optional.of(3)));
    }

    private Movie movieWith(List<UserRating> ratings) {
        return new Movie("99", "Love Boat", Arrays.asList(), ratings);
    }
}