package my.projects.videorecommendations.data;

import my.projects.videorecommendations.data.entities.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MovieRepository extends
        Repository<Movie, String>, JpaSpecificationExecutor<Movie> {

    @Query("SELECT m FROM Movie m")
    List<Movie> findAll();

    void save(Movie item);
}
