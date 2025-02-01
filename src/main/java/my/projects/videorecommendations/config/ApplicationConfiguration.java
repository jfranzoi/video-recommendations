package my.projects.videorecommendations.config;

import my.projects.videorecommendations.data.DataLoader;
import my.projects.videorecommendations.data.MovieRepository;
import my.projects.videorecommendations.init.InitActions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.nio.file.Path;

@Configuration
@EnableJpaRepositories(basePackages = "my.projects.videorecommendations.data")
public class ApplicationConfiguration {

    @Bean
    public DataLoader dataLoader(MovieRepository repository, @Value("${application.data.folder}") Path data) {
        return new DataLoader(repository, data);
    }

    @Bean
    public InitActions initActions(DataLoader dataLoader) {
        return new InitActions(dataLoader);
    }
}
