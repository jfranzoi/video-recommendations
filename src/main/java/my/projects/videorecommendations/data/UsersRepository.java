package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface UsersRepository extends Repository<User, String>, JpaSpecificationExecutor<User> {
    void save(User user);
}
