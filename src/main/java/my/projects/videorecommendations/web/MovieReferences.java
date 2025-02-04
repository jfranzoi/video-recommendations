package my.projects.videorecommendations.web;

import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.data.entities.UserRating;
import my.projects.videorecommendations.web.entities.MovieReference;
import my.projects.videorecommendations.web.entities.RatingReference;

public class MovieReferences {
    public MovieReference from(Movie movie) {
        return new MovieReference(
                movie.getId(), movie.getTitle(),
                movie.rating().orElse(null),
                movie.getRatings().stream().map(x -> toRatingReference(x)).toList(),
                movie.getGenres()
        );
    }

    private RatingReference toRatingReference(UserRating rating) {
        return new RatingReference(rating.getRating());
    }
}
