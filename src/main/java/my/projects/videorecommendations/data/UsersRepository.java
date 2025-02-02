package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UsersRepository extends Repository<User, String> {
    Optional<User> findById(String id);

    void save(User user);
}
