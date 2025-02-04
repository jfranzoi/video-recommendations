package my.projects.videorecommendations;

import my.projects.videorecommendations.tests.BaseAcceptanceTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MoviesCatalogAcceptanceTest extends BaseAcceptanceTest {

    private final HttpHeaders ACCEPT_JSON = new HttpHeaders() {{
        add("Accept", "application/json");
    }};

    @Test
    void listAllMovies() {
        ResponseEntity<String> result = get("/movies", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results", hasSize(10)));
        assertThat(result.getBody(), hasJsonPath("$.results[*].ratings[*].value", not(empty())));

        assertThat(result.getBody(), hasJsonPath("$.results[*].['title','rating']", hasItems(
                allOf(hasEntry("title", "Toy Story"), (Matcher) hasEntry("rating", 4)),
                allOf(hasEntry("title", "Grumpier Old Men"), (Matcher) hasEntry("rating", 3)),
                allOf(hasEntry("title", "Die Hard"), (Matcher) hasEntry("rating", 3)),
                allOf(hasEntry("title", "Star Wars: Return of the Jedi"), (Matcher) hasEntry("rating", 4))
        )));
    }

    @Test
    void withDetails() {
        ResponseEntity<String> result = get("/movies", ACCEPT_JSON);

        assertThat(result.getBody(), hasJsonPath("$.results[?(@.title == 'Toy Story')].genres[*]", contains(
                "Adventure", "Animation", "Children", "Comedy", "Fantasy"
        )));
    }

    @Test
    void listByGenre() {
        ResponseEntity<String> result = get("/movies?genre=Action", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results[*].title", containsInAnyOrder(
                "Die Hard",
                "Star Wars: Return of the Jedi",
                "The Matrix"
        )));
    }

    @Test
    void listByGenre_noResults() {
        ResponseEntity<String> result = get("/movies?genre=Horror", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results", empty()));
    }

    @Test
    void listByRating_min() {
        ResponseEntity<String> result = get("/movies?rating.min=4", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results[*].['title','rating']", containsInAnyOrder(
                allOf(hasEntry("title", "Toy Story"), (Matcher) hasEntry("rating", 4)),
                allOf(hasEntry("title", "Star Wars: Return of the Jedi"), (Matcher) hasEntry("rating", 4))
        )));
    }

    @Test
    void listByRating_max() {
        ResponseEntity<String> result = get("/movies?rating.max=3", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results[*].['title','rating']", contains(
                allOf(hasEntry("title", "Die Hard"), (Matcher) hasEntry("rating", 3))
        )));
    }
}
