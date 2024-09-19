alter table ems.employee
    add constraint employee_position_code_fkey
        foreign key (position) references ems.position(code);
