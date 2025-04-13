package hexlet.code.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Url {
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

    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return createdAt != null ? createdAt.format(formatter) : "";
    }
}
