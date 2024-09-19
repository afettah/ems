CREATE TABLE IF NOT EXISTS ems.i18n_message (
    id SERIAL PRIMARY KEY,
    "key" TEXT NOT NULL,
    locale TEXT NOT NULL,
    "value" TEXT NOT NULL
);
