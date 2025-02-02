package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.User;
import my.projects.videorecommendations.tests.PersistenceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

@PersistenceTest
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository repository;

    @Test
    void none() {
        assertThat(repository.findAll(null), empty());
    }

    @Test
    void saveOne() {
        repository.save(new User("1", "Alice"));
        assertThat(repository.findAll(null), contains(new User("1", "Alice")));
    }
}