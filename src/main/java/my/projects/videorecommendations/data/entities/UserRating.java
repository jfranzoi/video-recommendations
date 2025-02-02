package my.projects.videorecommendations.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Entity
@Table(name = "user_ratings")
@IdClass(UserRating.Key.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {
    @Id
    private String userId;
    @Id
    private String movieId;
    private int rating;

    @Convert(converter = Type.Converter.class)
    private Type type;

    @Data
    @NoArgsConstructor
    public static class Key {
        private String userId;
        private String movieId;
    }

    public enum Type {
        NONE("X"),
        EXPLICIT("E"),
        IMPLICIT("I");

        private final String code;

        Type(String code) {
            this.code = code;
        }

        static Type byCode(String text) {
            return Arrays.stream(Type.values())
                    .filter(x -> text.equals(x.code))
                    .findFirst().orElse(Type.NONE);
        }

        static class Converter implements AttributeConverter<Type, String> {

            @Override
            public String convertToDatabaseColumn(Type type) {
                return type.code;
            }

            @Override
            public Type convertToEntityAttribute(String text) {
                return Type.byCode(text);
            }

        }
    }
}
