package my.projects.videorecommendations.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@ComponentTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "application.data.folder=src/test/resources/data/full"
})
public abstract class BaseAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    protected ResponseEntity<String> get(String location, HttpHeaders headers) {
        return restTemplate.exchange(locally(location), HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    private String locally(String value) {
        return "http://localhost:" + port + value;
    }
}
