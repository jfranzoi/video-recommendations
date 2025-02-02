package my.projects.videorecommendations.dummies;

import my.projects.videorecommendations.data.EventsRepository;
import my.projects.videorecommendations.data.entities.UserEvent;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventsRepository implements EventsRepository {
    private ArrayList<UserEvent> events = new ArrayList<>();

    @Override
    public List<UserEvent> findAll() {
        return events;
    }

    @Override
    public void save(UserEvent item) {
        events.add(item);
    }
}
