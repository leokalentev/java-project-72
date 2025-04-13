package hexlet.code.controller;

import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlConroller {
    public static void build(Context ctx) {
        var page = new BuildUrlPage();
        var session = ctx.consumeSessionAttribute("flash");
        page.setFlash((String) session);
        ctx.render("index.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        try {
            var input = ctx.formParam("url");

            if (input == null || input.trim().isEmpty()) {
                ctx.sessionAttribute("flash", "Url не может быть пустым");
                ctx.redirect("/");
                return;
            }

            URI uri;
            try {
                uri = URI.create(input);
            } catch (IllegalArgumentException e) {
                ctx.sessionAttribute("flash", "Invalid URL");
                ctx.redirect("/");
                return;
            }

            URL url;
            try {
                url = uri.toURL();
            } catch (Exception e) {
                ctx.sessionAttribute("flash", "Invalid URL");
                ctx.redirect("/");
                return;
            }
            var protocol = url.getProtocol();
            var host = url.getHost();
            var port = url.getPort();

            var baseUrl = port == -1
                    ? String.format("%s://%s", protocol, host)
                    : String.format("%s://%s:%d", protocol, host, port);
            if (UrlRepository.getNames(baseUrl)) {
                ctx.sessionAttribute("flash", "The page already exists");
                ctx.redirect("/");
                return;
            }

            var newUrl = new Url(baseUrl);
            UrlRepository.save(newUrl);
            ctx.sessionAttribute("flash", "Страница добавлена!");
            ctx.redirect("/urls");
        } catch (ValidationException e) {
            var url = ctx.formParam("name");
            var page = new BuildUrlPage(url, e.getErrors());
            ctx.render("/", model("page", page));
        }
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var session = ctx.consumeSessionAttribute("flash");
        var page = new UrlsPage(urls);
        page.setFlash((String) session);
        ctx.render("urls.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));

        var session = ctx.consumeSessionAttribute("flash");
        var page = new UrlPage(url);
        page.setFlash((String) session);
        ctx.render("url.jte", model("page", page));
    }
}
