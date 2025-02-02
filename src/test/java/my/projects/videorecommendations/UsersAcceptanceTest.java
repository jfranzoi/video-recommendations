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

public class UsersAcceptanceTest extends BaseAcceptanceTest {

    @Test
    void listAllHistory() {
        ResponseEntity<String> result = get("/users/1", new HttpHeaders() {{
            add("Accept", "application/json");
        }});

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.user.name", is("Alice")));
        assertThat(result.getBody(), hasJsonPath("$.events[*].type", contains("rated", "rated")));
        assertThat(result.getBody(), hasJsonPath("$.events[*].movie", contains("1", "2")));
    }

    @Test
    void userNotFound() {
        ResponseEntity<String> result = get("/users/99", new HttpHeaders() {{
            add("Accept", "application/json");
        }});

        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
}
