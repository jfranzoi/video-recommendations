package my.projects.videorecommendations.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "user_events")
@IdClass(UserEvent.Key.class)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserEvent {

    @Data
    static class Key {
        private String userId;
        private String movieId;
    }

    @Id
    private String userId;

    @Id
    private String movieId;
}
