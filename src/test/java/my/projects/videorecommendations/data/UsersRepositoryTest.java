package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

@SpringBootTest(properties = {
        "application.data.folder=src/test/resources/data/empty"
})
@ActiveProfiles({"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository repository;

    @Test
    void none() {
        assertThat(repository.findAll(), empty());
    }

    @Test
    void saveOne() {
        repository.save(new User("1", "Alice"));
        assertThat(repository.findAll(), contains(new User("1", "Alice")));
    }
}