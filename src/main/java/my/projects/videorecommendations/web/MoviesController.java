package my.projects.videorecommendations.web;

import my.projects.videorecommendations.data.MovieRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.web.entities.MovieReference;
import my.projects.videorecommendations.web.entities.MovieResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MovieRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(MovieResults.builder()
                .results(toReferences(repository.findAll()))
                .build());
    }

    private List<MovieReference> toReferences(List<Movie> movies) {
        return movies.stream()
                .map(x -> toReference(x))
                .collect(Collectors.toList());
    }

    private MovieReference toReference(Movie movie) {
        return MovieReference.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .build();
    }
}
