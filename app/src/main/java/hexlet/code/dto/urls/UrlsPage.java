package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hexlet.code.model.UrlCheck;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UrlsPage extends BasePage {
    private List<Url> urls;
    private Map<Long, UrlCheck> lastChecks = new HashMap<>();

    public UrlsPage(List<Url> urls) {
        this.urls = urls;
    }
    public void setLastCheck(Long urlId, UrlCheck check) {
        lastChecks.put(urlId, check);
    }

    public UrlCheck getLastCheck(Long urlId) {
        return lastChecks.get(urlId);
    }
}
