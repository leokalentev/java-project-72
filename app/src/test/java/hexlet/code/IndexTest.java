package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;


public class IndexTest {
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
            assertThat(body).contains("Url cannot be empty");
        });
    }

    @Test
    public void testCreateInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls", "url=not-a-url");
            assertThat(response.code()).isEqualTo(200);
            var body = response.body().string();
            assertThat(body).contains("Invalid url");
        });
    }

    @Test
    public void testCreateUrlPageAlready() throws IOException, SQLException {
        JavalinTest.test(app, (server, client) -> {
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody("<html><title>Ignored</title><body></body></html>"));

            String testUrl = mockWebServer.url("/").toString();

            URI uri = URI.create(testUrl);
            URL urlObj = uri.toURL();
            String baseUrl = urlObj.getPort() == -1
                    ? String.format("%s://%s", urlObj.getProtocol(), urlObj.getHost())
                    : String.format("%s://%s:%d", urlObj.getProtocol(), urlObj.getHost(), urlObj.getPort());

            UrlRepository.save(new Url(baseUrl));
            var response = client.post("/urls", "url=" + testUrl);

            assertThat(response.code()).isEqualTo(200);

            String body = response.body().string();
            assertThat(body).contains("The page already exists");
        });
    }
}
