package my.projects.videorecommendations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
public class AppTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void applicationStarts() {
        ResponseEntity<String> result = restTemplate.getForEntity(locally("/actuator/env"), String.class);

        assertThat(result.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(result.getBody(), allOf(
                containsString("application.data.folder"),
                containsString("/data")
        ));
    }

    private String locally(String value) {
        return "http://localhost:" + port + value;
    }

}
