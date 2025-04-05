package hexlet.code.model;

import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Url {
    private Long id;
    private String name;
    private LocalDate createdAt;
}
