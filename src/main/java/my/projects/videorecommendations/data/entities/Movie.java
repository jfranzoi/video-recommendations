package my.projects.videorecommendations.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private String id;
    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> genres;

    @OneToMany(mappedBy = "movieId")
    private List<UserRating> ratings = new ArrayList<>();

    public Double rating() {
        return ratings.isEmpty() ? null :
                getRatings().stream()
                        .mapToDouble(x -> x.getRating())
                        .average().getAsDouble();
    }
}
