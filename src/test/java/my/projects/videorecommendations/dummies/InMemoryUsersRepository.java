package my.projects.videorecommendations.dummies;

import my.projects.videorecommendations.data.UsersRepository;
import my.projects.videorecommendations.data.entities.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUsersRepository extends EmptySpecificationRepository<User> implements UsersRepository {
    private ArrayList<User> users = new ArrayList<>();

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public void save(User item) {
        users.add(item);
    }
}
