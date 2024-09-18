create table ems.timeoff_category
(
    id          uuid                     not null primary key,
    name        text                     not null,
    description text,
    paid        boolean                  not null,
    auto_cancellable boolean                  not null,
    created_at  timestamp with time zone not null,
    updated_at  timestamp with time zone
);
