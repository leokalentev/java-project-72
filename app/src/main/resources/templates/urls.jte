@import hexlet.code.dto.urls.UrlsPage
@param UrlsPage page

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
            <h1 class="mb-4">Список сайтов</h1>
            <table class="table table-bordered table-striped">
                <thead class="table-primary">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Date of verification</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    @for (var url : page.getUrls())
                        <tr>
                            <td>${url.getId()}</td>
                            <td>${url.getName()}</td>
                            <td>${url.getFormattedCreatedAt()}</td>
                            <td>
                                <a href="/urls/${url.getId()}" class="btn btn-sm btn-primary">Подробнее</a>
                            </td>
                        </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    `
)