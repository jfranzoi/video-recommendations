package my.projects.videorecommendations.web;

import my.projects.videorecommendations.data.UsersRepository;
import my.projects.videorecommendations.data.entities.User;
import my.projects.videorecommendations.web.entities.UserHistory;
import my.projects.videorecommendations.web.entities.UserReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> byId(@PathVariable String id) {
        return repository.findBy(id)
                .map(x -> ResponseEntity.ok().body(toHistory(x)))
                .orElse(ResponseEntity.notFound().build());
    }

    private UserHistory toHistory(User user) {
        return new UserHistory(
                new UserReference(user.getId(), user.getName())
        );
    }
}
