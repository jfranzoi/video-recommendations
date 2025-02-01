package my.projects.videorecommendations.init;

import jakarta.annotation.PostConstruct;

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
