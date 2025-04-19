@import hexlet.code.dto.BasePage
@import hexlet.code.dto.urls.BuildUrlPage
@param BuildUrlPage page

@template.layout.page(
    content = @`
        @if(page.getFlash() != null)
            <div class="alert alert-danger">
                ${page.getFlash()}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif

        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6 text-center">
                    <h1 class="display-4 text-primary mb-4">Анализатор страниц</h1>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <form action="/urls" method="post" class="form-inline d-flex">
                        <div class="input-group">
                            <input type="text" placeholder="https://example.com" id="url-input" name="url" class="form-control form-control-lg" aria-label="URL">
                            <button type="submit" class="btn btn-primary btn-lg">Проверить</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row justify-content-center mt-3">
                <div class="col-md-6 text-center">
                    <a href="/urls" class="btn btn-outline-secondary">Добавленные сайты</a>
                </div>
            </div>
        </div>
    `
)