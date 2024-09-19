create table ems.employee
(
    id         uuid                     not null primary key,
    email      text                     not null,
    name       text                     not null,
    position   text                     not null,
    salary     ${jsonType},
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone
);
