package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.User;
import my.projects.videorecommendations.tests.PersistenceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@PersistenceTest
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository repository;

    @Test
    void none() {
        assertThat(repository.findById("1"), is(Optional.empty()));
    }

    @Test
    void saveOne() {
        repository.save(new User("1", "Alice"));
        assertThat(repository.findById("1"), is(Optional.of(new User("1", "Alice"))));
    }
}