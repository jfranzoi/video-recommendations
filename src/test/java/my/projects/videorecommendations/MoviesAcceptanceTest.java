package my.projects.videorecommendations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "application.data.folder=src/test/resources/data/full"
})
@ActiveProfiles({"test"})
public class MoviesAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void listAllMovies() {
        ResponseEntity<String> result = get(locally("/movies"), new HttpHeaders() {{
            add("Accept", "application/json");
        }});

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results", hasSize(10)));
    }

    @Test
    void listByGenre() {
        ResponseEntity<String> result = get(locally("/movies?genre=Action"), new HttpHeaders() {{
            add("Accept", "application/json");
        }});

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), hasJsonPath("$.results[*].title", containsInAnyOrder(
                "Die Hard",
                "Star Wars: Return of the Jedi",
                "The Matrix"
        )));
    }

    private ResponseEntity<String> get(String location, HttpHeaders headers) {
        return restTemplate.exchange(location, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    private String locally(String value) {
        return "http://localhost:" + port + value;
    }
}
