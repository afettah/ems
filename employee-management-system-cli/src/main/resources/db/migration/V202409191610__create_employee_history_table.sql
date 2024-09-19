CREATE TABLE IF NOT EXISTS ems.employee_history (
    id SERIAL PRIMARY KEY,
    username TEXT,
    action TEXT NOT NULL,
    changes ${jsonType},
    created_at timestamp with time zone not null
);
