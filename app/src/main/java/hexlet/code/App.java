package hexlet.code;

import hexlet.code.controller.UrlConroller;
import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.validation.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static io.javalin.rendering.template.TemplateUtil.model;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    private static String readResourceFile(String fileName) throws IOException {
        try (InputStream inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8))) { // Явное указание UTF-8
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
    public static void main(String[] args) throws SQLException, IOException {
        var app = getApp();
        setupUtf8(app);
        app.start(7070);
    }
    public static Javalin getApp() throws IOException, SQLException {
        var env = System.getenv().getOrDefault("APP_ENV", "development");
        var hikariConfig = new HikariConfig();

        if ("production".equals(env)) {
            var dbUrl = System.getenv("JDBC_DATABASE_URL");
            var dbUser = System.getenv("DB_USERNAME");
            var dbPassword = System.getenv("DB_PASSWORD");

            hikariConfig.setJdbcUrl(dbUrl);
            hikariConfig.setUsername(dbUser);
            hikariConfig.setPassword(dbPassword);
        } else {
            hikariConfig.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        }

        var dataSource = new HikariDataSource(hikariConfig);

        if ("development".equals(env)) {
            var sql = readResourceFile("schema.sql");
            log.info(sql);
            try (var connection = dataSource.getConnection();
                 var statement = connection.createStatement()) {
                statement.execute(sql);
            }
        }

        BaseRepository.dataSource = dataSource;

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(NamedRoutes.rootPath(), UrlConroller::build);
        app.post(NamedRoutes.urlsPath(), UrlConroller::create);
        app.get(NamedRoutes.urlsPath(), UrlConroller::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlConroller::show);
        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        System.setProperty("jte.charset", "UTF-8");
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }

    private static void setupUtf8(Javalin app) {
        app.before(ctx -> {
            ctx.contentType("text/html; charset=utf-8");
            ctx.header("Content-Type", "text/html; charset=utf-8");
        });
    }
}
