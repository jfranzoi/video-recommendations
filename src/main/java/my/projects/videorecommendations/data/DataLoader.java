package my.projects.videorecommendations.data;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.entities.Movie;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class DataLoader {
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
        process(data.resolve("movies.csv"), MovieRow.class, x -> {
            repository.save(new Movie());
        });
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
            return mapper.readerFor(type)
                    .with(mapper.schemaFor(type).withHeader())
                    .readValues(source.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    static class MovieRow {
        private String movieId;
        private String title;
        private List<String> genres;
    }
}
