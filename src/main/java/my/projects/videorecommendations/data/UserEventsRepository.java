package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.UserEvent;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserEventsRepository extends Repository<UserEvent, String> {
    List<UserEvent> findAll();

    void save(UserEvent item);
}
