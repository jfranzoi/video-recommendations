package my.projects.videorecommendations;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApplicationAcceptanceTest extends BaseAcceptanceTest {

    @Test
    void applicationStarts() {
        ResponseEntity<String> result = get("/actuator/env", new HttpHeaders());

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody(), allOf(
                containsString("application.data.folder"),
                containsString("/data")
        ));
    }

}
