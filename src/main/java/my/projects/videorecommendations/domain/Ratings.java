package my.projects.videorecommendations.domain;

import my.projects.videorecommendations.data.entities.MovieRatedEvent;
import my.projects.videorecommendations.data.entities.MovieViewedEvent;
import my.projects.videorecommendations.data.entities.UserEvent;
import my.projects.videorecommendations.data.entities.UserRating;

import java.util.Optional;

public class Ratings {

    public Optional<UserRating> valueFor(UserEvent event) {
        if (event instanceof MovieRatedEvent) return valueFor((MovieRatedEvent) event);
        if (event instanceof MovieViewedEvent) return valueFor((MovieViewedEvent) event);
        return Optional.empty();
    }

    private Optional<UserRating> valueFor(MovieRatedEvent event) {
        return Optional.of(new UserRating(event.getUserId(), event.getMovieId(), event.getRating()));
    }

    private Optional<UserRating> valueFor(MovieViewedEvent event) {
        return Optional.of(event)
                .filter(x -> event.getPercentage() > 0)
                .map(x -> new UserRating(x.getUserId(), x.getMovieId(), 5));
    }
}
