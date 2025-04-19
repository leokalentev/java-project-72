@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.dto.urls.UrlCheckPage
@param UrlPage page
@param UrlCheckPage checkPage


@template.layout.page(
    content = @`
        @if(page.getFlash() != null)
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${page.getFlash()}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif

        <div class="container mt-5">
            <h1 class="mb-4">Информация о сайте</h1>
            <table class="table table-bordered table-striped">
                <thead class="table-primary">
                    <tr>
                        <th>ID</th>
                        <th>Имя</th>
                        <th>Дата создания</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${page.getUrl().getId()}</td>
                        <td>${page.getUrl().getName()}</td>
                        <td>${page.getUrl().getFormattedCreatedAt()}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="container mt-5">
            <h1 class="mb-4">Проверки</h1>
            <form method="post" action="/urls/${page.getUrl().getId()}" class="mb-4">
                <button type="submit" class="btn btn-primary">Запустить проверку</button>
            </form>
            <table class="table table-bordered table-striped">
                <thead class="table-primary">
                    <tr>
                        <th>ID</th>
                        <th>Status Code</th>
                        <th>Title</th>
                        <th>H1</th>
                        <th>Description</th>
                        <th>Date of verification</th>
                    </tr>
                </thead>
                <tbody>
                    @for (var urlCheck : checkPage.getUrlChecks())
                        <tr>
                            <td>${urlCheck.getId()}</td>
                            <td>${urlCheck.getStatusCode()}</td>
                            <td>${urlCheck.getTitle()}</td>
                            <td>${urlCheck.getH1()}</td>
                            <td>${urlCheck.getDescription()}</td>
                            <td>${urlCheck.getFormattedCreatedAt()}</td>
                        </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    `
)