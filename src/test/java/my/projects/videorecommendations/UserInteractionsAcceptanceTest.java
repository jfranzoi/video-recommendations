package my.projects.videorecommendations;

import my.projects.videorecommendations.tests.BaseAcceptanceTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserInteractionsAcceptanceTest extends BaseAcceptanceTest {

    @Test
    void ingestMovieRated() {
        HashMap<String, String> event = new HashMap<>() {{
            put("type", "rated");
            put("value", "5");
            put("movie", "6");
            put("user", "1");
        }};

        ResponseEntity<String> result = put("/events", event, new HttpHeaders() {{
            add("Content-Type", "application/json");
        }});

        assertThat(result.getStatusCode(), is(HttpStatus.ACCEPTED));

        ResponseEntity<String> movies = get("/movies", new HttpHeaders() {{
            add("Accept", "application/json");
        }});

        assertThat(movies.getBody(), hasJsonPath("$.results[*].['title','rating']", hasItem(
                allOf(hasEntry("title", "Pulp Fiction"), (Matcher) hasEntry("rating", 5.0))
        )));
    }
}
