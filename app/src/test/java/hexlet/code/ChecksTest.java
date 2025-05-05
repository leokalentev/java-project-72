package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public final class ChecksTest {

    private Javalin app;
    private static MockWebServer mockWebServer;

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        app = App.getApp();
        UrlRepository.removeAll();
    }

    @BeforeAll
    public static void startMock() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    public static void endMock() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testCheckUrl() throws Exception {
        String fakeHtml = Files.readString(Path.of("src/test/resources/test-response.html"),
                StandardCharsets.UTF_8);
        mockWebServer.enqueue(new MockResponse().setBody(fakeHtml).setResponseCode(200));

        String fakeUrl = mockWebServer.url("/").toString();
        var url = new Url(fakeUrl);
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls/" + url.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);

            var checks = UrlCheckRepository.find(url.getId());
            assertThat(checks).isNotEmpty();
            var latestCheck = checks.get(0);
            assertThat(latestCheck.getStatusCode()).isEqualTo(200);
            assertThat(latestCheck.getTitle()).isNotBlank();
            assertThat(latestCheck.getH1()).isNotBlank();
            assertThat(latestCheck.getDescription()).isNotBlank();


            var urlPage = client.get("/urls/" + url.getId());
            var responseBody = urlPage.body().string();

            assertThat(responseBody).contains("ID");
            assertThat(responseBody).contains("Status Code");
            assertThat(responseBody).contains("Title");
            assertThat(responseBody).contains("H1");
            assertThat(responseBody).contains("Description");
            assertThat(responseBody).contains("Date of verification");
        });
    }
}
