CREATE TABLE "UrlShortener" (
    "id" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "url" VARCHAR(100) DEFAULT NULL,
    "shortUrl" VARCHAR(10)
);