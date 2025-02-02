package my.projects.videorecommendations.data.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class MovieRatedEvent extends UserEvent {
    private int rate;

    public MovieRatedEvent(String userId, String movieId, int rate) {
        super(userId, movieId);
        this.rate = rate;
    }
}
