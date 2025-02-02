package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface MoviesRepository extends Repository<Movie, String>, JpaSpecificationExecutor<Movie> {
    void save(Movie item);
}
