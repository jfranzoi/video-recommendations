package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends Repository<User, String> {

    @Query("SELECT u FROM User u where id = :id")
    Optional<User> findBy(@Param("id") String id);

    List<User> findAll();

    void save(User user);

}
