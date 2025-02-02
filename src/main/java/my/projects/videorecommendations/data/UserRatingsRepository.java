package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.UserRating;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface UserRatingsRepository extends Repository<UserRating, UserRating.Key>, JpaSpecificationExecutor<UserRating> {
    void save(UserRating rating);
}
