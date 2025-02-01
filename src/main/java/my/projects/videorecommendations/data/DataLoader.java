package my.projects.videorecommendations.data;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.entities.Movie;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

@Slf4j
public class DataLoader {
    private static final String COLLECTION_SEPARATOR = Pattern.quote("|");

    private final MovieRepository repository;
    private final Path data;
    private final CsvMapper mapper;

    public DataLoader(MovieRepository repository, Path data) {
        this.repository = repository;
        this.data = data;
        this.mapper = new CsvMapper();
    }

    public void process() {
        log.info("Processing data at {}", data);
        process(data.resolve("movies.csv"), MovieRow.class, x -> repository.save(
                Movie.builder()
                        .id(x.movie_id)
                        .title(x.title)
                        .genres(toCollection(x.genres))
                        .build()
        ));
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

    private List<String> toCollection(String value) {
        return Arrays.asList(value.split(COLLECTION_SEPARATOR));
    }

    @Data
    static class MovieRow {
        private String movie_id;
        private String title;
        private String genres;
    }
}
