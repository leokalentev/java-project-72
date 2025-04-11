package gg.jte.generated.ondemand;
import hexlet.code.dto.urls.UrlsPage;
public final class JteurlsGenerated {
	public static final String JTE_NAME = "urls.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,3,3,5,5,6,6,8,8,8,11,11,21,21,23,23,23,24,24,24,25,25,25,27,27,27,27,31,31,35,35,35,35,35,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    ");
				if (page.getFlash() != null) {
					jteOutput.writeContent("\r\n        <div class=\"alert\">\r\n            ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(page.getFlash());
					jteOutput.writeContent("\r\n            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n        </div>\r\n    ");
				}
				jteOutput.writeContent("\r\n        <div>\r\n          <h1>Сайты</h1>\r\n          <table>\r\n            <th>\r\n              <tr>ID</tr>\r\n              <tr>Имя</tr>\r\n              <tr>Дата проверки</tr>\r\n            </th>\r\n            <tbody>\r\n                ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent(" {\r\n                    <tr>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("</td>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</td>\r\n                        <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getFormattedCreatedAt());
					jteOutput.writeContent("</td>\r\n                        <td>\r\n                            <a href=\"/urls/");
					jteOutput.setContext("a", "href");
					jteOutput.writeUserContent(url.getId());
					jteOutput.setContext("a", null);
					jteOutput.writeContent("\" class=\"btn btn-sm btn-primary\">Подробнее</a>\r\n                        </td>\r\n                    </tr>\r\n                }\r\n                ");
				}
				jteOutput.writeContent("\r\n            </tbody>\r\n          </table>\r\n        </div>\r\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
