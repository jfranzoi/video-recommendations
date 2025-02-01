package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> all();

    void save(Movie item);
}
