DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP
);

CREATE TABLE url_checks (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status_code BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    h1 VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    url_id BIGINT NOT NULL,
    created_at TIMESTAMP,
    CONSTRAINT fk_url FOREIGN KEY (url_id) REFERENCES urls(id) ON DELETE CASCADE
);
