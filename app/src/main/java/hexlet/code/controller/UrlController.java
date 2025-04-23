package hexlet.code.controller;

import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.dto.urls.UrlCheckPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.net.URL;

import java.sql.SQLException;
import java.util.List;


import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {
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
                var page = new BuildUrlPage();
                page.setFlash("Url \u043D\u0435 \u043C\u043E\u0436\u0435\u0442 \u0431\u044B\u0442\u044C "
                        + "\u043F\u0443\u0441\u0442\u044B\u043C");
                ctx.render("index.jte", model("page", page));
                return;
            }

            URI uri;
            try {
                uri = URI.create(input);
            } catch (IllegalArgumentException e) {
                var page = new BuildUrlPage();
                page.setFlash("\u041D\u0435\u0434\u043E\u043F\u0443\u0441\u0442\u0438\u043C\u044B\u0439 "
                        + "URL-\u0430\u0434\u0440\u0435\u0441");
                ctx.render("index.jte", model("page", page));

                return;
            }

            URL url;
            try {
                url = uri.toURL();
            } catch (Exception e) {
                var page = new BuildUrlPage();
                page.setFlash("\u041D\u0435\u0434\u043E\u043F\u0443\u0441\u0442\u0438\u043C\u044B\u0439 "
                        + "URL-\u0430\u0434\u0440\u0435\u0441");
                ctx.render("index.jte", model("page", page));
                return;
            }
            var protocol = url.getProtocol();
            var host = url.getHost();
            var port = url.getPort();

            var baseUrl = port == -1
                    ? String.format("%s://%s", protocol, host)
                    : String.format("%s://%s:%d", protocol, host, port);
            if (UrlRepository.getNames(baseUrl)) {
                var page = new BuildUrlPage();
                page.setFlash("Url \u0443\u0436\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442");
                ctx.render("index.jte", model("page", page));
                return;
            }

            var newUrl = new Url(baseUrl);
            UrlRepository.save(newUrl);
            ctx.sessionAttribute("flash", "Url \u0434\u043E\u0431\u0430\u0432\u043B\u0435\u043D!");
            ctx.redirect("/urls");
        } catch (ValidationException e) {
            var url = ctx.formParam("name");
            var page = new BuildUrlPage(url, e.getErrors());
            ctx.render("index.jte", model("page", page));
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
        var check = UrlCheckRepository.getEntities();

        var session = ctx.consumeSessionAttribute("flash");
        var page = new UrlPage(url);
        var checks = new UrlCheckPage(check);

        page.setFlash((String) session);
        ctx.render("url.jte", model("page", page, "checkPage", checks));
    }

    public static void check(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();

            int statusCode = response.getStatus();
            if (statusCode >= 400) {
                throw new RuntimeException("Failed to fetch the page: HTTP " + statusCode);
            }

            String html = response.getBody();
            Document document = Jsoup.parse(html);

            String title = document.title();
            String h1 = document.selectFirst("h1") != null ? document.selectFirst("h1").text() : null;
            String description = document.selectFirst("meta[name=description]") != null
                    ? document.selectFirst("meta[name=description]").attr("content")
                    : null;

            var check = new UrlCheck(statusCode, title, h1, description, url.getId());
            UrlCheckRepository.save(check);

            ctx.sessionAttribute("flash", "URL успешно проверен!");

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Error checking URL: " + e.getMessage());
        }

        showChecks(ctx);
    }

    public static void showChecks(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));

        List<UrlCheck> checks = UrlCheckRepository.find(id);
        var session = ctx.consumeSessionAttribute("flash");

        var page = new UrlPage(url);
        page.setFlash((String) session);

        var checkPage = new UrlCheckPage(checks);

        ctx.render("checks.jte", model("page", page, "checkPage", checkPage));
    }
}
