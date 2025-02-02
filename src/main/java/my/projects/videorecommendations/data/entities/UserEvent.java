package my.projects.videorecommendations.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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
