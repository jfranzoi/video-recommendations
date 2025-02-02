package my.projects.videorecommendations.dummies;

import my.projects.videorecommendations.data.UsersRepository;
import my.projects.videorecommendations.data.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUsersRepository implements UsersRepository {
    private ArrayList<User> users = new ArrayList<>();

    public List<User> all() {
        return users;
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream()
                .filter(x -> id.equals(x.getId()))
                .findFirst();
    }

    @Override
    public void save(User item) {
        users.add(item);
    }
}
