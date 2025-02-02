package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.UserEvent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface UserEventsRepository extends Repository<UserEvent, String>, JpaSpecificationExecutor<UserEvent> {
    void save(UserEvent item);
}
