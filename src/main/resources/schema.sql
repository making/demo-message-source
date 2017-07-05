CREATE TABLE message_resource (
  code     VARCHAR(128) NOT NULL,
  language VARCHAR(128) NOT NULL,
  message  VARCHAR(255) NOT NULL,
  PRIMARY KEY (code, language)
);