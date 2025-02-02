package my.projects.videorecommendations.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "user_events")
@Data
@NoArgsConstructor
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;
    private String movieId;

    public UserEvent(String userId, String movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}
