package my.projects.videorecommendations.domain;

import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.MovieViewedEvent;
import my.projects.videorecommendations.data.entities.UserEvent;
import my.projects.videorecommendations.data.entities.UserRating;
import my.projects.videorecommendations.data.entities.UserRating.Type;

import java.util.Optional;

public class Ratings {

    public Optional<UserRating> rate(UserEvent event) {
        if (event instanceof MovieRatedEvent) return rate((MovieRatedEvent) event);
        if (event instanceof MovieViewedEvent) return rate((MovieViewedEvent) event);
        return Optional.empty();
    }

    private Optional<UserRating> rate(MovieRatedEvent event) {
        return Optional.of(rateAs(event.getRating(), event, Type.EXPLICIT));
    }

    private Optional<UserRating> rate(MovieViewedEvent event) {
        return rateStartingFrom(80, 5, event).or(() -> rateStartingFrom(60, 4, event));
    }

    private Optional<UserRating> rateStartingFrom(int threshold, int value, MovieViewedEvent event) {
        return Optional.of(event)
                .filter(x -> event.getPercentage() >= threshold)
                .map(x -> rateAs(value, x, Type.IMPLICIT));
    }

    private UserRating rateAs(int value, UserEvent event, Type type) {
        return new UserRating(
                event.getUserId(), event.getMovieId(),
                value, type
        );
    }
}
