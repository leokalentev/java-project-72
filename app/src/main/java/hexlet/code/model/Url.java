package hexlet.code.model;

import java.time.LocalDateTime;

import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class Url {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public Url(String name) {
        this.name = name;
    }

    public Url(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
