@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

@template.layout.page(
    content = @`
        @if(page.getFlash() != null)
            <div class="alert">
                ${page.getFlash()}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
        <div>
          <h1>Сайт</h1>
          <table>
            <th>
              <tr>ID</tr>
              <tr>Имя</tr>
              <tr>Дата проверки</tr>
            </th>
            <tbody>
                <td>${page.getUrl().getId()}</td>
                <td>${page.getUrl().getName()}</td>
                <td>${page.getUrl().getFormattedCreatedAt()}</td>
            </tbody>
          </table>
        </div>
    `
)