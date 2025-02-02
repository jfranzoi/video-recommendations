package my.projects.videorecommendations.init;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.MovieRepository;
import my.projects.videorecommendations.data.UserRepository;
import my.projects.videorecommendations.data.entities.Movie;
import my.projects.videorecommendations.data.entities.User;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

@Slf4j
public class DataLoader {
    private static final String COLLECTION_SEPARATOR = Pattern.quote("|");

    private final MovieRepository movies;
    private final UserRepository users;
    private final CsvMapper mapper;

    public DataLoader(MovieRepository movies, UserRepository users) {
        this.movies = movies;
        this.users = users;
        this.mapper = new CsvMapper();
    }

    public void process(Path data) {
        log.info("Processing data at {}", data);
        process(data.resolve("movies.csv"), MovieRow.class, x -> movies.save(toMovie(x)));
        process(data.resolve("users.csv"), UserRow.class, x -> users.save(toUser(x)));
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
        return Movie.builder()
                .id(row.movie_id)
                .title(row.title)
                .genres(toCollection(row.genres))
                .build();
    }

    private User toUser(UserRow row) {
        return User.builder()
                .id(row.user_id)
                .name(row.username)
                .build();
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
}
