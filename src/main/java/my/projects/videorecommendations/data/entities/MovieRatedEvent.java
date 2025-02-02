package my.projects.videorecommendations.data.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("rated")
@Data
@NoArgsConstructor
public class MovieRatedEvent extends UserEvent {
    private int rating;

    public MovieRatedEvent(String userId, String movieId, int rating) {
        super(userId, movieId);
        this.rating = rating;
    }
}
