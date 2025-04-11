@import hexlet.code.dto.urls.UrlsPage
@param UrlsPage page

@template.layout.page(
    content = @`
    @if(page.getFlash() != null)
        <div class="alert">
            ${page.getFlash()}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @endif
        <div>
          <h1>Сайты</h1>
          <table>
            <th>
              <tr>ID</tr>
              <tr>Имя</tr>
              <tr>Дата проверки</tr>
            </th>
            <tbody>
                @for (var url : page.getUrls()) {
                    <tr>
                        <td>${url.getId()}</td>
                        <td>${url.getName()}</td>
                        <td>${url.getFormattedCreatedAt()}</td>
                        <td>
                            <a href="/urls/${url.getId()}" class="btn btn-sm btn-primary">Подробнее</a>
                        </td>
                    </tr>
                }
                @endfor
            </tbody>
          </table>
        </div>
    `
)