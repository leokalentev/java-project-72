package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

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

        assertNotNull(url.getId(), "URL ID should not be null after saving");

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://java-page-analyzer-ru.hexlet.app/");
        });
    }

    @Test
    public void testNotFoundUrl() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/909090");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testAddUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "name=https://java-page-analyzer-ru.hexlet.app/";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string().contains("https://java-page-analyzer-ru.hexlet.app/"));
        });
    }

    @Test
    public void testCheckUrl() throws SQLException {
        String fakeHtml = """
                 <tr>ID</tr>
                 <tr>Status code</tr>
                 <tr>Title</tr>
                 <tr>H1</tr>
                 <tr>Description</tr>
                 <tr>Date of verification</tr>
                """;
        mockWebServer.enqueue(new MockResponse().setBody(fakeHtml).setResponseCode(200));

        String fakeUrl = mockWebServer.url("/").toString();
        var url = new Url(fakeUrl);
        UrlRepository.save(url);


        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);

            var urlPage = client.get("/urls/" + url.getId());
            var responseBody = urlPage.body().string();

            assertThat(responseBody).contains("ID");
            assertThat(responseBody).contains("Status code");
            assertThat(responseBody).contains("Title");
            assertThat(responseBody).contains("H1");
            assertThat(responseBody).contains("Description");
            assertThat(responseBody).contains("Date of verification");
        });
    }
}
