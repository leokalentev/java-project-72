@import hexlet.code.dto.BasePage
@import hexlet.code.dto.urls.BuildUrlPage
@param BuildUrlPage page

@template.layout.page(
    content = @`
        @if(page.getFlash() != null)
            <div class="alert">
                ${page.getFlash()}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif

         @if(page.getErrors() != null)
            <div class="mb-3">
                <ul>
                    @for(var validator : page.getErrors().values())
                        @for(var error : validator)
                            <li>${error.getMessage()}</li>
                        @endfor
                    @endfor
                </ul>
            </div>
         @endif

        <div>
            <form action="/urls" method="post">
                <input type="text" placeholder="https://example.com" id="url-input" name="url">
                <label for="url-input">Ссылка</label>
            </form>
        </div>
        <div>
            <a href="/urls">Добавленные сайты</a>
        </div>
    `
)