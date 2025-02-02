package my.projects.videorecommendations.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    private String id;
    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> genres;
}
