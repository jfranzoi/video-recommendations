package my.projects.videorecommendations.domain;

import lombok.extern.slf4j.Slf4j;
import my.projects.videorecommendations.data.UsersRepository;
import my.projects.videorecommendations.data.entities.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

@Slf4j
public class Users {
    private final UsersRepository usersRepository;

    public Users(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> by(String id) {
        log.info("Collecting user, by: [{}]", id);
        return usersRepository.findOne(byId(id));
    }

    private Specification<User> byId(String id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }
}
