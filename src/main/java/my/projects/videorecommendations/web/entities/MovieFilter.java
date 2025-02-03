package my.projects.videorecommendations.web.entities;

import lombok.Data;

@Data
public class MovieFilter {
    private String genre;
    private RatingFilter rating = new RatingFilter();

    @Data
    public static class RatingFilter {
        private Integer min;
        private Integer max;
    }
}
