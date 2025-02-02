package my.projects.videorecommendations.config;

import my.projects.videorecommendations.data.UserRepository;
import my.projects.videorecommendations.init.DataLoader;
import my.projects.videorecommendations.data.MovieRepository;
import my.projects.videorecommendations.init.InitActions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.nio.file.Path;

@Configuration
@EnableJpaRepositories(basePackages = "my.projects.videorecommendations.data")
@ComponentScan(basePackages = "my.projects.videorecommendations.web")
public class ApplicationConfiguration {

    @Bean
    public DataLoader dataLoader(MovieRepository movies, UserRepository users) {
        return new DataLoader(movies, users);
    }

    @Bean
    public InitActions initActions(DataLoader dataLoader, @Value("${application.data.folder}") Path data) {
        return new InitActions(dataLoader, data);
    }
}
