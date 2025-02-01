package my.projects.videorecommendations.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieReference {
    private String id;
    private String title;
}
