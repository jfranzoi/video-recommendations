package my.projects.videorecommendations.web.entities;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class MovieResults {
    @Builder.Default
    private List<MovieReference> results = new ArrayList<>();
}
