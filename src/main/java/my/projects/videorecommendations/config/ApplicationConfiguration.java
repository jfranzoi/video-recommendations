package my.projects.videorecommendations.config;

import my.projects.videorecommendations.data.MoviesRepository;
import my.projects.videorecommendations.data.UserEventsRepository;
import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.UsersRepository;
import my.projects.videorecommendations.init.DataLoader;
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
    public DataLoader dataLoader(MoviesRepository movies, UsersRepository users, UserEventsRepository events, UserRatingsRepository ratings) {
        return new DataLoader(movies, users, events, ratings);
    }

    @Bean
    public InitActions initActions(DataLoader dataLoader, @Value("${application.data.folder}") Path data) {
        return new InitActions(dataLoader, data);
    }
}
