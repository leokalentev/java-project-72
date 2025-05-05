package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;


public final class IndexTest {
    private Javalin app;

    @BeforeEach
    public void setUp() throws SQLException, IOException {
        app = App.getApp();
        UrlRepository.removeAll();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Page Analyzer");
        });
    }

    @Test
    public void testCreateEmptyUrl() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls", "url=");
            assertThat(response.code()).isEqualTo(200);

            var body = response.body().string();
            assertThat(body).contains("Url not empty");

            var allUrls = UrlRepository.getEntities();
            assertThat(allUrls).isEmpty();
        });
    }

    @Test
    public void testCreateInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls", "url=not-a-url");
            assertThat(response.code()).isEqualTo(200);

            var body = response.body().string();
            assertThat(body).contains("Invalid url");

            var urls = UrlRepository.getEntities();
            assertThat(urls).isEmpty();
        });
    }

    @Test
    public void testCreateUrlPageAlready() {
        JavalinTest.test(app, (server, client) -> {
            String testUrl = "https://example.com";
            UrlRepository.save(new Url(testUrl));

            var response = client.post("/urls", "url=" + testUrl);
            assertThat(response.code()).isEqualTo(200);

            String body = response.body().string();
            assertThat(body).
                    contains("Url is already there");

            var urls = UrlRepository.getEntities();
            assertThat(urls).hasSize(1);
            assertThat(urls.get(0).getName()).isEqualTo(testUrl);
        });
    }
}
