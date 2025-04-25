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


public class UrlsTest {
    private Javalin app;

    @BeforeEach
    public void setUp() throws SQLException, IOException {
        app = App.getApp();
        UrlRepository.removeAll();
    }

    @Test
    public void testUrlsPage() throws SQLException {
        var testUrl = "http://example.com";
        var url = new Url(testUrl);
        UrlRepository.save(url);
        assertThat(UrlRepository.find(url.getId())).isNotEmpty();

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            var responseBody = response.body().string();

            assertThat(response.code()).isEqualTo(200);

            assertThat(responseBody).contains("ID");
            assertThat(responseBody).contains("Name");
            assertThat(responseBody).contains("Date of verification");
            assertThat(responseBody).contains("Last check created at");
            assertThat(responseBody).contains("Last check status code");
            assertThat(responseBody).contains("Actions");

            assertThat(responseBody).contains(testUrl);
        });
    }
}
