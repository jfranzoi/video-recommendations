package my.projects.videorecommendations.init;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.MoviesRepository;
import my.projects.videorecommendations.data.UserEventsRepository;
import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.UsersRepository;
import my.projects.videorecommendations.data.entities.*;
import my.projects.videorecommendations.domain.MovieRatings;
import my.projects.videorecommendations.domain.UserEvents;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Pattern;

@Slf4j
public class DataLoader {
    private static final String COLLECTION_SEPARATOR = Pattern.quote("|");

    private final MoviesRepository moviesRepository;
    private final UsersRepository usersRepository;
    private final UserEventsRepository userEventsRepository;
    private final UserRatingsRepository userRatingsRepository;
    private final CsvMapper mapper;

    public DataLoader(MoviesRepository moviesRepository, UsersRepository usersRepository, UserEventsRepository userEventsRepository, UserRatingsRepository userRatingsRepository) {
        this.moviesRepository = moviesRepository;
        this.usersRepository = usersRepository;
        this.userEventsRepository = userEventsRepository;
        this.userRatingsRepository = userRatingsRepository;
        this.mapper = new CsvMapper();
    }

    public void process(Path data) {
        log.info("Processing data at {}", data);

        process(data.resolve("movies.csv"), MovieRow.class, x -> moviesRepository.save(toMovie(x)));
        process(data.resolve("users.csv"), UserRow.class, x -> usersRepository.save(toUser(x)));
        process(data.resolve("ratings.csv"), RatingRow.class, x -> {
                    UserEvent event = toEvent(x);
                    new UserEvents(userEventsRepository).on(event);
                    new MovieRatings(userRatingsRepository).on(event);
                }
        );
    }

    private <T> void process(Path source, Class<T> type, Consumer<T> action) {
        try (MappingIterator<T> items = itemsAt(source, type)) {
            items.forEachRemaining(action);
        } catch (IOException e) {
            log.warn("Skip processing [{}]. Cause: [{}]", source, e.getMessage(), e);
        }
    }

    private <T> MappingIterator<T> itemsAt(Path source, Class<T> type) {
        try {
            log.info("Processing items at: [{}]", source);
            return mapper.readerFor(type)
                    .with(mapper.schemaFor(type).withHeader().withColumnReordering(true))
                    .readValues(source.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Movie toMovie(MovieRow row) {
        return new Movie(row.movie_id, row.title, toCollection(row.genres));
    }

    private User toUser(UserRow row) {
        return new User(row.user_id, row.username);
    }

    private UserEvent toEvent(RatingRow row) {
        return Optional.ofNullable(row.rating)
                .map(x -> toMovieRated(row))
                .orElseGet(() -> toMovieViewed(row));
    }

    private UserEvent toMovieRated(RatingRow row) {
        return new MovieRatedEvent(row.user_id, row.movie_id, row.rating);
    }

    private MovieViewedEvent toMovieViewed(RatingRow row) {
        return new MovieViewedEvent(row.user_id, row.movie_id, row.view_percentage);
    }

    private List<String> toCollection(String value) {
        return Arrays.asList(value.split(COLLECTION_SEPARATOR));
    }

    @Data
    static class MovieRow {
        private String movie_id;
        private String title;
        private String genres;
    }

    @Data
    static class UserRow {
        private String user_id;
        private String username;
    }

    @Data
    static class RatingRow {
        private String user_id;
        private String movie_id;
        private Integer rating;
        private Integer view_percentage;
    }
}
