package my.projects.videorecommendations.domain;

import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.MovieViewedEvent;
import my.projects.videorecommendations.data.entities.UserRating;
import my.projects.videorecommendations.data.entities.UserRating.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RatingPoliciesTest {
    private static final String ANY = "99";

    @Test
    void explicit() {
        Optional<UserRating> result = new RatingPolicies().rate(new MovieRatedEvent(ANY, ANY, 5));
        assertThat(result, is(Optional.of(new UserRating(ANY, ANY, 5, Type.EXPLICIT))));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 20, 30, 40, 50, 59})
    void implicit_skipped(int percentage) {
        Optional<UserRating> result = new RatingPolicies().rate(new MovieViewedEvent(ANY, ANY, percentage));
        assertThat(result, is(Optional.empty()));
    }

    @ParameterizedTest
    @CsvSource({
            "60,4",
            "70,4",
            "79,4",
            "80,5",
            "90,5",
            "100,5",
    })
    void implicit_rated(int percentage, int expected) {
        Optional<UserRating> result = new RatingPolicies().rate(new MovieViewedEvent(ANY, ANY, percentage));
        assertThat(result, is(Optional.of(new UserRating(ANY, ANY, expected, Type.IMPLICIT))));
    }
}