package my.projects.videorecommendations;

import my.projects.videorecommendations.tests.BaseAcceptanceTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class UsersHistoryAcceptanceTest extends BaseAcceptanceTest {

    private final HttpHeaders ACCEPT_JSON = new HttpHeaders() {{
        add("Accept", "application/json");
    }};

    @Test
    void listAllHistory() {
        ResponseEntity<String> result = get("/users/1", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.user.name", is("Alice")));
        assertThat(result.getBody(), hasJsonPath("$.events[*].type", contains("rated", "viewed", "rated")));
        assertThat(result.getBody(), hasJsonPath("$.events[*].movie", contains("1", "1", "2")));
    }

    @Test
    void listRatingsOnly() {
        ResponseEntity<String> result = get("/users/2?type=rated", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.user.name", is("Bob")));
        assertThat(result.getBody(), hasJsonPath("$.events[*].type", contains("rated")));
        assertThat(result.getBody(), hasJsonPath("$.events[*].movie", contains("3")));
    }

    @Test
    void listViewsOnly() {
        ResponseEntity<String> result = get("/users/2?type=viewed", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.user.name", is("Bob")));
        assertThat(result.getBody(), hasJsonPath("$.events[*].type", contains("viewed")));
        assertThat(result.getBody(), hasJsonPath("$.events[*].movie", contains("1")));
    }

    @Test
    void userNotFound() {
        ResponseEntity<String> result = get("/users/99", ACCEPT_JSON);

        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
}
