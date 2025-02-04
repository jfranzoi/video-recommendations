package my.projects.videorecommendations;

import my.projects.videorecommendations.tests.BaseAcceptanceTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class UserRecommendationsAcceptanceTest extends BaseAcceptanceTest {

    private final HttpHeaders ACCEPT_JSON = new HttpHeaders() {{
        add("Accept", "application/json");
    }};

    @Test
    void similarToPreferred() {
        ResponseEntity<String> result = get("/recommendations/1", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results[*].title", containsInAnyOrder(
                "Star Wars: Return of the Jedi",
                "The Lion King",
                "Forrest Gump",
                "Jurassic Park"
        )));
    }

    @Test
    void similarToHighlyRatedOnly() {
        ResponseEntity<String> result = get("/recommendations/2", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results[*].title", containsInAnyOrder(
                "Grumpier Old Men",
                "Star Wars: Return of the Jedi",
                "The Lion King",
                "Forrest Gump",
                "Jurassic Park"
        )));
    }
}
