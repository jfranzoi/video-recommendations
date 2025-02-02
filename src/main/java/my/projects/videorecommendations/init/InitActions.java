package my.projects.videorecommendations.init;

import jakarta.annotation.PostConstruct;

import java.nio.file.Path;

public class InitActions {

    private final DataLoader dataLoader;
    private final Path data;

    public InitActions(DataLoader dataLoader, Path data) {
        this.dataLoader = dataLoader;
        this.data = data;
    }

    @PostConstruct
    public void init() {
        dataLoader.process(data);
    }
}
