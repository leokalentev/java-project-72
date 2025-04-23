package hexlet.code;

import hexlet.code.controller.UrlController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.resolve.ResourceCodeResolver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    private static String readResourceFile(String fileName) throws IOException {
        ClassLoader classLoader = App.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + fileName);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        System.setProperty("jte.charset", "UTF-8");
        var app = getApp();
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

        app.before(ctx -> {
            ctx.contentType("text/html; charset=utf-8");
            ctx.header("Content-Type", "text/html; charset=utf-8");
        });
        app.get(NamedRoutes.rootPath(), UrlController::build);
        app.post(NamedRoutes.urlsPath(), UrlController::create);
        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        app.post("/urls/{id}/checks", UrlController::check);
        app.get("/urls/{id}/checks", UrlController::showChecks);
        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        System.setProperty("jte.charset", "UTF-8");
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
}
