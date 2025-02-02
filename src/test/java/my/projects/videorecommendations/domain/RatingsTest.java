package my.projects.videorecommendations.domain;

import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.MovieViewedEvent;
import my.projects.videorecommendations.data.entities.UserRating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RatingsTest {
    private static final String ANY = "99";

    @Test
    void explicit() {
        Optional<UserRating> result = new Ratings().rate(new MovieRatedEvent(ANY, ANY, 5));
        assertThat(result.map(x -> x.getRating()), is(Optional.of(5)));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 20, 30, 40, 50, 59})
    void implicit_skipped(int percentage) {
        Optional<UserRating> result = new Ratings().rate(new MovieViewedEvent(ANY, ANY, percentage));
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
        Optional<UserRating> result = new Ratings().rate(new MovieViewedEvent(ANY, ANY, percentage));
        assertThat(result.map(x -> x.getRating()), is(Optional.of(expected)));
    }
}