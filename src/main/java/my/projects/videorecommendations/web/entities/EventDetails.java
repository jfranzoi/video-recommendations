package my.projects.videorecommendations.web.entities;

import lombok.Data;

@Data
public class EventDetails {
    private String type;
    private String value;
    private String movie;
    private String user;
}
