package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.UserRating;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRatingsRepository extends Repository<UserRating, UserRating.Key> {
    List<UserRating> findAll();

    void save(UserRating rating);
}
