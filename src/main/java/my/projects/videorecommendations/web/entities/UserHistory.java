package my.projects.videorecommendations.web.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHistory {
    private UserReference user;
    private List<UserEventReference> events;
}
