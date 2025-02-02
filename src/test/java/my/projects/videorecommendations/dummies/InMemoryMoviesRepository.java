package my.projects.videorecommendations.dummies;

import my.projects.videorecommendations.data.MoviesRepository;
import my.projects.videorecommendations.data.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMoviesRepository extends EmptySpecificationRepository<Movie> implements MoviesRepository {
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    public List<Movie> findAll() {
        return movies;
    }

    @Override
    public void save(Movie item) {
        movies.add(item);
    }
}
