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

import static hexlet.code.repository.UrlRepository.getNames;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class UrlTest {
    private Javalin app;
    private static MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws SQLException, IOException {
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
    public void testUrlPage() throws SQLException {
        var url = new Url("https://java-page-analyzer-ru.hexlet.app/");
        UrlRepository.save(url);

        var saved = UrlRepository.find(url.getId());
        assertThat(saved).isPresent();
        assertThat(saved.get().getName()).isEqualTo(url.getName());

        assertNotNull(url.getId(), "URL ID should not be null after saving");

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://java-page-analyzer-ru.hexlet.app/");
        });
    }

    @Test
    public void testNotFoundUrl() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/909090");
            assertThat(response.code()).isEqualTo(404);
        });

        var notFound = UrlRepository.find(909090L);
        assertThat(notFound).isEmpty();
    }

    @Test
    public void testAddUrl() {
        JavalinTest.test(app, (server, client) -> {
            var url = "https://java-page-analyzer-ru.hexlet.app";
            var requestBody = "url=" + url;
            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains(url);
            assertThat(getNames(url)).isTrue();
        });
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
            var response = client.post("/urls/" + url.getId());
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
