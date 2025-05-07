@import hexlet.code.dto.urls.UrlsPage
@import java.time.format.DateTimeFormatter
@param UrlsPage page
@param DateTimeFormatter formatter

@template.layout.page(
    content = @`
        @if(page.getFlash() != null)
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${page.getFlash()}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif

        <div class="container mt-5">
            <h1 class="mb-4">Список сайтов</h1>
            <table class="table table-bordered table-striped">
                <thead class="table-primary">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Date of verification</th>
                        <th>Last check created at</th>
                        <th>Last check status code</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    @for (var url : page.getUrls())
                        <tr>
                            <td>${url.getId()}</td>
                            <td>${url.getName()}</td>
                            <td>${url.getCreatedAt().format(formatter)}</td>
                            @if (page.getLastCheck(url.getId()) != null)
                                <td>${page.getLastCheck(url.getId()).getCreatedAt().format(formatter)}</td>
                                <td>${page.getLastCheck(url.getId()).getStatusCode()}</td>
                            @else
                                <td></td>
                                <td></td>
                            @endif
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
