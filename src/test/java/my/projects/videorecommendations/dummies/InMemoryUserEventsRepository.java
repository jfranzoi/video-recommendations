package my.projects.videorecommendations.dummies;

import my.projects.videorecommendations.data.UserEventsRepository;
import my.projects.videorecommendations.data.entities.UserEvent;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserEventsRepository implements UserEventsRepository {
    private ArrayList<UserEvent> events = new ArrayList<>();

    @Override
    public List<UserEvent> findByUserId(String userId) {
        return events.stream()
                .filter(x -> userId.equals(x.getUserId()))
                .toList();
    }

    @Override
    public List<UserEvent> findAll() {
        return events;
    }

    @Override
    public void save(UserEvent item) {
        events.add(item);
    }
}
