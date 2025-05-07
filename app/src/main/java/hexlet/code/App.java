package hexlet.code;

import hexlet.code.controller.UrlController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@Slf4j
public class App {
    private static String readResourceFile(String fileName) throws IOException {
        ClassLoader classLoader = App.class.getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream(fileName)) {
            if (input == null) {
                throw new FileNotFoundException("Resource not found: " + fileName);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        System.setProperty("jte.charset", "UTF-8");
        getApp().start(7070);
    }

    public static Javalin getApp() throws IOException, SQLException {
        System.setProperty("jte.charset", "UTF-8");
        String env = System.getenv().getOrDefault("APP_ENV", "development");

        HikariConfig config = new HikariConfig();

        if ("test".equals(env)) {
            config.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
            config.setDriverClassName("org.h2.Driver");

        } else {
            String dbUrl = System.getenv("JDBC_DATABASE_URL");
            if (dbUrl != null && !dbUrl.isEmpty()) {
                config.setJdbcUrl(dbUrl);
                config.setUsername(System.getenv("DB_USERNAME"));
                config.setPassword(System.getenv("DB_PASSWORD"));
            } else if ("production".equals(env)) {
                throw new IllegalStateException("JDBC_DATABASE_URL must be set in production");
            } else {
                config.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
                config.setDriverClassName("org.h2.Driver");
            }
        }

        var dataSource = new HikariDataSource(config);
        BaseRepository.dataSource = dataSource;

        if (!"production".equals(env)) {
            var sql = readResourceFile("schema.sql");
            try (var conn = dataSource.getConnection();
                 var stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        }

        Javalin app = Javalin.create(cfg -> {
            cfg.bundledPlugins.enableDevLogging();
            cfg.fileRenderer(new JavalinJte(createTemplateEngine()));
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

        return app;
    }


    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver resolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(resolver, ContentType.Html);
    }
}
