package my.projects.videorecommendations;

import my.projects.videorecommendations.tests.BaseAcceptanceTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UsersInteractionsAcceptanceTest extends BaseAcceptanceTest {

    private final HttpHeaders CONTENT_TYPE_JSON = new HttpHeaders() {{
        add("Content-Type", "application/json");
    }};

    private final HttpHeaders ACCEPT_JSON = new HttpHeaders() {{
        add("Accept", "application/json");
    }};

    @Test
    void ingestMovieRated() {
        ResponseEntity<String> result = put("/events", CONTENT_TYPE_JSON, Map.of(
                "type", "rated", "value", "5",
                "movie", "6", "user", "1"
        ));
        assertThat(result.getStatusCode(), is(HttpStatus.ACCEPTED));

        ResponseEntity<String> movies = get("/movies", ACCEPT_JSON);
        assertThat(movies.getBody(), hasJsonPath("$.results[?(@.title == 'Pulp Fiction')].ratings[*]", contains(
                hasEntry("value", 5)
        )));
    }

    @Test
    void ingestMovieViewed() {
        ResponseEntity<String> result = put("/events", CONTENT_TYPE_JSON, Map.of(
                "type", "viewed", "value", "100",
                "movie", "5", "user", "1"
        ));
        assertThat(result.getStatusCode(), is(HttpStatus.ACCEPTED));

        ResponseEntity<String> movies = get("/movies", ACCEPT_JSON);
        assertThat(movies.getBody(), hasJsonPath("$.results[?(@.title == 'The Lion King')].ratings[*]", contains(
                hasEntry("value", 5)
        )));
    }

    @Test
    void ingestMovieViewedThenRated() {
        put("/events", CONTENT_TYPE_JSON, Map.of(
                "type", "viewed", "value", "100",
                "movie", "7", "user", "1"
        ));

        put("/events", CONTENT_TYPE_JSON, Map.of(
                "type", "rated", "value", "1",
                "movie", "7", "user", "1"
        ));

        ResponseEntity<String> movies = get("/movies", ACCEPT_JSON);
        assertThat(movies.getBody(), hasJsonPath("$.results[?(@.title == 'Forrest Gump')].ratings[*]", contains(
                hasEntry("value", 1)
        )));
    }

    @Test
    void ingestMovieRatedThenViewed() {
        put("/events", CONTENT_TYPE_JSON, Map.of(
                "type", "rated", "value", "1",
                "movie", "8", "user", "1"
        ));

        put("/events", CONTENT_TYPE_JSON, Map.of(
                "type", "viewed", "value", "100",
                "movie", "8", "user", "1"
        ));

        ResponseEntity<String> movies = get("/movies", ACCEPT_JSON);
        assertThat(movies.getBody(), hasJsonPath("$.results[?(@.title == 'The Matrix')].ratings[*]", contains(
                hasEntry("value", 1)
        )));
    }
}
