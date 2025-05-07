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
import java.time.format.DateTimeFormatter;
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
                page.setFlash("Url not empty");
                ctx.render("index.jte", model("page", page));
                return;
            }

            URI uri;
            try {
                uri = URI.create(input);
            } catch (IllegalArgumentException e) {
                var page = new BuildUrlPage();
                page.setFlash("Invalid Url");
                ctx.render("index.jte", model("page", page));

                return;
            }

            URL url;
            try {
                url = uri.toURL();
            } catch (Exception e) {
                var page = new BuildUrlPage();
                page.setFlash("Invalid url");
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
                page.setFlash("Url is already there");
                ctx.render("index.jte", model("page", page));
                return;
            }

            var newUrl = new Url(baseUrl);
            UrlRepository.save(newUrl);
            ctx.sessionAttribute("flash", "Url added!");
            ctx.redirect("/urls");
        } catch (ValidationException e) {
            var url = ctx.formParam("name");
            var page = new BuildUrlPage(url, e.getErrors());
            ctx.render("index.jte", model("page", page));
        }
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var lastChecks = UrlCheckRepository.getLastChecksForAllUrls();

        var page = new UrlsPage(urls);
        page.setLastChecks(lastChecks);

        var flash = ctx.consumeSessionAttribute("flash");
        page.setFlash((String) flash);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        ctx.render("urls.jte", model("page", page, "formatter", formatter));
    }


    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));

        List<UrlCheck> checks = UrlCheckRepository.find(id);
        var session = ctx.consumeSessionAttribute("flash");

        var page = new UrlPage(url);
        page.setFlash((String) session);

        var checkPage = new UrlCheckPage(checks);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        ctx.render("url.jte", model("page", page, "checkPage", checkPage, "formatter", formatter));
    }


    public static void check(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));

        int statusCode;
        String title = "";
        String h1 = "";
        String description = "";

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            statusCode = response.getStatus();

            String html = response.getBody();
            Document document = Jsoup.parse(html);

            title = document.title() != null ? document.title() : "";
            h1 = document.selectFirst("h1") != null ? document.selectFirst("h1").text() : "";
            description = document.selectFirst("meta[name=description]") != null
                    ? document.selectFirst("meta[name=description]").attr("content")
                    : "";

            ctx.sessionAttribute("flash", "URL check!");
        } catch (Exception e) {
            statusCode = 500;
            ctx.sessionAttribute("flash", "Error: " + e.getMessage());
        }

        var check = new UrlCheck(statusCode, title, h1, description, url.getId());
        UrlCheckRepository.save(check);

        show(ctx);
    }
}
