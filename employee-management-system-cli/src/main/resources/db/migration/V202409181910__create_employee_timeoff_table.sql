create table ems.employee_timeoff
(
    id          uuid                     not null primary key,
    employee_id uuid                     not null,
    category_id    uuid                     not null,
    start_date  date                     not null,
    end_date    date                     not null,
    status      text                     not null,
    comment     text,
    created_at  timestamp with time zone not null,
    updated_at  timestamp with time zone
);

alter table ems.employee_timeoff
    add constraint employee_timeoff_employee_id_fkey
        foreign key (employee_id) references ems.employee (id);
alter table ems.employee_timeoff
    add constraint employee_timeoff_category_fkey
        foreign key (category_id) references ems.timeoff_category (id);
