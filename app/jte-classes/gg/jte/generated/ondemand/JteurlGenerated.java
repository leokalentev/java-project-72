package gg.jte.generated.ondemand;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlCheckPage;
public final class JteurlGenerated {
	public static final String JTE_NAME = "url.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,6,6,8,8,9,10,10,12,12,12,15,15,17,30,30,30,31,31,31,32,32,32,38,41,41,41,41,56,56,58,58,58,59,59,59,60,60,60,61,61,61,62,62,62,63,63,63,65,65,69,69,69,69,69,2,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page, UrlCheckPage checkPage) {
		jteOutput.writeContent("\r\n\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n        ");
				jteOutput.writeContent("\r\n        ");
				if (page.getFlash() != null) {
					jteOutput.writeContent("\r\n            <div class=\"alert alert-success alert-dismissible fade show\" role=\"alert\">\r\n                ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(page.getFlash());
					jteOutput.writeContent("\r\n                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n            </div>\r\n        ");
				}
				jteOutput.writeContent("\r\n\r\n        ");
				jteOutput.writeContent("\r\n        <div class=\"container mt-5\">\r\n            <h1 class=\"mb-4\">Информация о сайте</h1>\r\n            <table class=\"table table-bordered table-striped\">\r\n                <thead class=\"table-primary\">\r\n                    <tr>\r\n                        <th>ID</th>\r\n                        <th>Имя</th>\r\n                        <th>Дата создания</th>\r\n                    </tr>\r\n                </thead>\r\n                <tbody>\r\n                    <tr>\r\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\r\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\r\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getFormattedCreatedAt());
				jteOutput.writeContent("</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n        </div>\r\n\r\n        ");
				jteOutput.writeContent("\r\n        <div class=\"container mt-5\">\r\n            <h1 class=\"mb-4\">Проверки</h1>\r\n            <form method=\"post\" action=\"/urls/");
				jteOutput.setContext("form", "action");
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.setContext("form", null);
				jteOutput.writeContent("\" class=\"mb-4\">\r\n                <button type=\"submit\" class=\"btn btn-primary\">Запустить проверку</button>\r\n            </form>\r\n            <table class=\"table table-bordered table-striped\">\r\n                <thead class=\"table-primary\">\r\n                    <tr>\r\n                        <th>ID</th>\r\n                        <th>Status Code</th>\r\n                        <th>Title</th>\r\n                        <th>H1</th>\r\n                        <th>Description</th>\r\n                        <th>Дата проверки</th>\r\n                    </tr>\r\n                </thead>\r\n                <tbody>\r\n                    ");
				for (var urlCheck : checkPage.getUrlChecks()) {
					jteOutput.writeContent("\r\n                        <tr>\r\n                            <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getId());
					jteOutput.writeContent("</td>\r\n                            <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getStatusCode());
					jteOutput.writeContent("</td>\r\n                            <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getTitle());
					jteOutput.writeContent("</td>\r\n                            <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getH1());
					jteOutput.writeContent("</td>\r\n                            <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getDescription());
					jteOutput.writeContent("</td>\r\n                            <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlCheck.getFormattedCreatedAt());
					jteOutput.writeContent("</td>\r\n                        </tr>\r\n                    ");
				}
				jteOutput.writeContent("\r\n                </tbody>\r\n            </table>\r\n        </div>\r\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		UrlCheckPage checkPage = (UrlCheckPage)params.get("checkPage");
		render(jteOutput, jteHtmlInterceptor, page, checkPage);
	}
}
