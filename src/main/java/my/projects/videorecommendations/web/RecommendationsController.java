package my.projects.videorecommendations.web;

import my.projects.videorecommendations.data.MoviesRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.domain.Recommendations;
import my.projects.videorecommendations.web.entities.MovieResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {

    @Autowired
    private MoviesRepository moviesRepository;

    @RequestMapping(path = "/{user}", method = RequestMethod.GET)
    public ResponseEntity<?> byUser(@PathVariable String user) {
        List<Movie> movies = new Recommendations(moviesRepository).byUser(user);
        return ResponseEntity.ok(new MovieResults(
                movies.stream().map(x -> new MovieReferences().from(x)).toList()
        ));
    }

}
