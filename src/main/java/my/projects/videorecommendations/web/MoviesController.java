package my.projects.videorecommendations.web;

import my.projects.videorecommendations.data.MoviesRepository;
import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.data.entities.UserRating;
import my.projects.videorecommendations.domain.MovieRatings;
import my.projects.videorecommendations.domain.Movies;
import my.projects.videorecommendations.web.entities.MovieFilter;
import my.projects.videorecommendations.web.entities.MovieReference;
import my.projects.videorecommendations.web.entities.MovieResults;
import my.projects.videorecommendations.web.entities.RatingReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private UserRatingsRepository ratingsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> all(MovieFilter filter) {
        List<Movie> movies = new Movies(moviesRepository).all(filter);
        return ResponseEntity.ok(new MovieResults(
                movies.stream().map(x -> toMovieReference(x)).toList()
        ));
    }

    private MovieReference toMovieReference(Movie movie) {
        List<UserRating> ratings = new MovieRatings(ratingsRepository).byMovie(movie);
        return new MovieReference(
                movie.getId(), movie.getTitle(),
                ratings.stream().map(x -> toRatingReference(x)).toList()
        );
    }

    private RatingReference toRatingReference(UserRating rating) {
        return new RatingReference(rating.getRating());
    }
}
