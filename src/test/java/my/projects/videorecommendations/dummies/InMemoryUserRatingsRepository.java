package my.projects.videorecommendations.dummies;

import my.projects.videorecommendations.data.UserRatingsRepository;
import my.projects.videorecommendations.data.entities.UserRating;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRatingsRepository implements UserRatingsRepository {
    private ArrayList<UserRating> ratings = new ArrayList<>();

    @Override
    public List<UserRating> findAll() {
        return ratings;
    }

    @Override
    public void save(UserRating item) {
        ratings.add(item);
    }
}
