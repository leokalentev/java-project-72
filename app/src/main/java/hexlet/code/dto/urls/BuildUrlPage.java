package hexlet.code.dto.urls;
import java.util.List;
import java.util.Map;

import hexlet.code.dto.BasePage;
import io.javalin.validation.ValidationError;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuildUrlPage extends BasePage {
    private String name;
    private Map<String, List<ValidationError<Object>>> errors;
}
