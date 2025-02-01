package my.projects.videorecommendations.init;

import jakarta.annotation.PostConstruct;
import my.projects.videorecommendations.data.DataLoader;

public class InitActions {

    private DataLoader dataLoader;

    public InitActions(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @PostConstruct
    public void init() {
        dataLoader.process();
    }
}
