@import java.time.format.DateTimeFormatter
@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.dto.urls.UrlCheckPage
@param UrlPage page
@param UrlCheckPage checkPage
@param DateTimeFormatter formatter

@template.layout.page(
    content = @`
        @if(page.getFlash() != null)
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${page.getFlash()}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif

        <div class="container mt-4">
            <nav class="navbar navbar-expand-lg navbar-light bg-light rounded mb-4 px-3">
                <a class="navbar-brand" href="/">Page Analyzer</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="/">Главная</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/urls">Сайты</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>

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
                        <td>${page.getUrl().getCreatedAt().format(formatter)}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="container mt-5">
            <h1 class="mb-4">Проверки</h1>
            <form method="post" action="/urls/${page.getUrl().getId()}/checks" class="mb-4">
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
                            <td>${urlCheck.getCreatedAt().format(formatter)}</td>
                        </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    `
)
