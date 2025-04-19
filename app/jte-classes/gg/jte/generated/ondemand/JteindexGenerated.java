package gg.jte.generated.ondemand;
import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.BuildUrlPage;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,4,4,6,6,7,7,9,9,9,12,12,36,36,36,36,36,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, BuildUrlPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n        ");
				if (page.getFlash() != null) {
					jteOutput.writeContent("\r\n            <div class=\"alert alert-danger\">\r\n                ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(page.getFlash());
					jteOutput.writeContent("\r\n                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n            </div>\r\n        ");
				}
				jteOutput.writeContent("\r\n\r\n        <div class=\"container mt-5\">\r\n            <div class=\"row justify-content-center\">\r\n                <div class=\"col-md-6 text-center\">\r\n                    <h1 class=\"display-4 text-primary mb-4\">Анализатор страниц</h1>\r\n                </div>\r\n            </div>\r\n            <div class=\"row justify-content-center\">\r\n                <div class=\"col-md-6\">\r\n                    <form action=\"/urls\" method=\"post\" class=\"form-inline d-flex\">\r\n                        <div class=\"input-group\">\r\n                            <input type=\"text\" placeholder=\"https://example.com\" id=\"url-input\" name=\"url\" class=\"form-control form-control-lg\" aria-label=\"URL\">\r\n                            <button type=\"submit\" class=\"btn btn-primary btn-lg\">Проверить</button>\r\n                        </div>\r\n                    </form>\r\n                </div>\r\n            </div>\r\n            <div class=\"row justify-content-center mt-3\">\r\n                <div class=\"col-md-6 text-center\">\r\n                    <a href=\"/urls\" class=\"btn btn-outline-secondary\">Добавленные сайты</a>\r\n                </div>\r\n            </div>\r\n        </div>\r\n    ");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		BuildUrlPage page = (BuildUrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
