package my.projects.videorecommendations.config;

import my.projects.videorecommendations.data.DataLoader;
import my.projects.videorecommendations.data.InMemoryMoviesRepository;
import my.projects.videorecommendations.data.MovieRepository;
import my.projects.videorecommendations.init.InitActions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public DataLoader dataLoader(MovieRepository repository, @Value("${application.data.folder}") Path data) {
        return new DataLoader(repository, data);
    }

    @Bean
    public MovieRepository movieRepository() {
        return new InMemoryMoviesRepository();
    }

    @Bean
    public InitActions initActions(DataLoader dataLoader) {
        return new InitActions(dataLoader);
    }
}
