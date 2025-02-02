package my.projects.videorecommendations;

import my.projects.videorecommendations.tests.BaseAcceptanceTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MoviesAcceptanceTest extends BaseAcceptanceTest {

    @Test
    void listAllMovies() {
        ResponseEntity<String> result = get("/movies", new HttpHeaders() {{
            add("Accept", "application/json");
        }});

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results", hasSize(10)));
        assertThat(result.getBody(), hasJsonPath("$.results[*].ratings[*].value", not(empty())));
    }

    @Test
    void listByGenre() {
        ResponseEntity<String> result = get("/movies?genre=Action", new HttpHeaders() {{
            add("Accept", "application/json");
        }});

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results[*].title", containsInAnyOrder(
                "Die Hard",
                "Star Wars: Return of the Jedi",
                "The Matrix"
        )));
    }

    @Test
    void listByGenre_noResults() {
        ResponseEntity<String> result = get("/movies?genre=Horror", new HttpHeaders() {{
            add("Accept", "application/json");
        }});

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results", empty()));
    }
}
