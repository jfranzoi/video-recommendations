package my.projects.videorecommendations.web.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserHistory {
    private UserReference user;
}
