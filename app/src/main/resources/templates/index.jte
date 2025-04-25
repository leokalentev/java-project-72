@import hexlet.code.dto.BasePage
@import hexlet.code.dto.urls.BuildUrlPage
@param BuildUrlPage page

@template.layout.page(
    content = @`
         @if(page.getFlash() != null)
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
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
                                <a class="nav-link active" aria-current="page" href="/">Главная</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/urls">Сайты</a>
                            </li>
                        </ul>
                    </div>
            </nav>
         </div>

         <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6 text-center">
                    <h1 class="display-4 text-primary mb-4">Page Analyzer</h1>
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
         </div>
    `
)