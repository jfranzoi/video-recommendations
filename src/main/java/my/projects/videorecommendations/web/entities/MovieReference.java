package my.projects.videorecommendations.web.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieReference {
    private String id;
    private String title;
    private Integer rating;
    private List<RatingReference> ratings;
    private List<String> genres;
}
