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
import java.io.IOException;
import java.sql.SQLException;


public class UrlsTest {
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
    public void testUrlsPage() throws SQLException {
        String fakeHtml = """
                 <tr>ID</tr>
                 <tr>Name</tr>
                 <tr>Date of verification</tr>
                 <tr>Actions</tr>
                """;
        mockWebServer.enqueue(new MockResponse().setBody(fakeHtml).setResponseCode(200));

        String fakeUrl = mockWebServer.url("/urls").toString();
        var url = new Url(fakeUrl);
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            var responseBody = response.body().string();

            assertThat(responseBody).contains("ID");
            assertThat(responseBody).contains("Name");
            assertThat(responseBody).contains("Date of verification");
            assertThat(responseBody).contains("Actions");
            assertThat(response.code()).isEqualTo(200);
        });
    }
}
