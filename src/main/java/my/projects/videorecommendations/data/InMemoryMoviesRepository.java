package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMoviesRepository implements MovieRepository {
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    public List<Movie> all() {
        return movies;
    }

    @Override
    public void save(Movie item) {
        movies.add(item);
    }
}
