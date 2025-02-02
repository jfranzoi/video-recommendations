package my.projects.videorecommendations.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_ratings")
@IdClass(UserRating.Key.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {
    @Id
    private String userId;
    @Id
    private String movieId;
    private int rating;
    private String type;

    @Data
    @NoArgsConstructor
    public static class Key {
        private String userId;
        private String movieId;
    }
}
