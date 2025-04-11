package gg.jte.generated.ondemand;
import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.BuildUrlPage;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,4,4,6,6,7,7,9,9,9,12,12,14,14,17,17,18,18,19,19,19,20,20,21,21,24,24,35,35,35,35,35,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, BuildUrlPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n        ");
				if (page.getFlash() != null) {
					jteOutput.writeContent("\r\n            <div class=\"alert\">\r\n                ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(page.getFlash());
					jteOutput.writeContent("\r\n                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n            </div>\r\n        ");
				}
				jteOutput.writeContent("\r\n\r\n         ");
				if (page.getErrors() != null) {
					jteOutput.writeContent("\r\n            <div class=\"mb-3\">\r\n                <ul>\r\n                    ");
					for (var validator : page.getErrors().values()) {
						jteOutput.writeContent("\r\n                        ");
						for (var error : validator) {
							jteOutput.writeContent("\r\n                            <li>");
							jteOutput.setContext("li", null);
							jteOutput.writeUserContent(error.getMessage());
							jteOutput.writeContent("</li>\r\n                        ");
						}
						jteOutput.writeContent("\r\n                    ");
					}
					jteOutput.writeContent("\r\n                </ul>\r\n            </div>\r\n         ");
				}
				jteOutput.writeContent("\r\n\r\n        <div>\r\n            <form action=\"/urls\" method=\"post\">\r\n                <input type=\"text\" placeholder=\"https://example.com\" id=\"url-input\" name=\"url\">\r\n                <label for=\"url-input\">Ссылка</label>\r\n            </form>\r\n        </div>\r\n        <div>\r\n            <a href=\"/urls\">Добавленные сайты</a>\r\n        </div>\r\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		BuildUrlPage page = (BuildUrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
