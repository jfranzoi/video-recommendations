package my.projects.videorecommendations.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

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
    @CollectionTable(name = "movie_genres")
    private List<String> genres;

    @OneToMany(mappedBy = "movieId")
    private List<UserRating> ratings = new ArrayList<>();

    public Optional<Integer> rating() {
        return ratings.isEmpty() ? Optional.empty() :
                toRating(getRatings().stream()
                        .mapToDouble(x -> x.getRating()));
    }

    private Optional<Integer> toRating(DoubleStream values) {
        return Optional.of(new BigDecimal(values.average().getAsDouble()).intValue());
    }
}
