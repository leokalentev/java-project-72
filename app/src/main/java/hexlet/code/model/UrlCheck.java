package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlCheck {
    private Long id;
    private int statusCode;
    private String title;
    private String h1;
    @Lob
    private String description;
    private Long urlId;
    private LocalDateTime createdAt;

    public UrlCheck(int statusCode, String testTitle, String testH1, String testDescription, Long id) {
        this.statusCode = statusCode;
        this.title = testTitle;
        this.h1 = testH1;
        this.description = testDescription;
        this.urlId = id;
    }

    public UrlCheck(int statusCode, String testTitle, String testH1, String testDescription) {
        this.statusCode = statusCode;
        this.title = testTitle;
        this.h1 = testH1;
        this.description = testDescription;
    }

    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return createdAt != null ? createdAt.format(formatter) : "";
    }
}
