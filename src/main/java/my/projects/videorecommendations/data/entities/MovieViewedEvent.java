package my.projects.videorecommendations.data.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("viewed")
@Data
@NoArgsConstructor
public class MovieViewedEvent extends UserEvent {
    private int percentage;

    public MovieViewedEvent(String userId, String movieId, int percentage) {
        super(userId, movieId);
        this.percentage = percentage;
    }
}
