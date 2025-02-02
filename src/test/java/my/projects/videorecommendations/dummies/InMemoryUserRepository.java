package my.projects.videorecommendations.dummies;

import my.projects.videorecommendations.data.UserRepository;
import my.projects.videorecommendations.data.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private ArrayList<User> users = new ArrayList<>();

    @Override
    public Optional<User> findBy(String id) {
        return users.stream()
                .filter(x -> id.equals(x.getId()))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public void save(User item) {
        users.add(item);
    }
}
