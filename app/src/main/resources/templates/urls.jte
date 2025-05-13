@import hexlet.code.dto.urls.UrlsPage
@import java.time.format.DateTimeFormatter
@param UrlsPage page

@template.layout.page(
    content = @`
        !{var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");}

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
                        !{var lastCheck = page.getLastCheck(url.getId());}
                        <tr>
                            <td>${url.getId()}</td>
                            <td>${url.getName()}</td>
                            <td>${url.getCreatedAt().format(formatter)}</td>
                            @if (lastCheck != null)
                                <td>${lastCheck.getCreatedAt().format(formatter)}</td>
                                <td>${lastCheck.getStatusCode()}</td>
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
