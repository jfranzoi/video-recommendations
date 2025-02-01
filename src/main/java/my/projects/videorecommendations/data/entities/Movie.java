package my.projects.videorecommendations.data.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Movie {
    private String id;
    private String title;
    private List<String> genres;
}
