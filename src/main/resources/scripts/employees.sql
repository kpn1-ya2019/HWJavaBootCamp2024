create table if not exists public.employees
(
    id   integer not null,
    iddepartment integer not null,
    constraint employees_pk
    primary key (id, iddepartment),
    surname text,
    name text,
    patronymic text,
    salary numeric
);


comment on table public.employees is 'Сотрудники';

comment on column public.employees.id is 'Идентификатор';
comment on column public.employees.surname is 'Фамилия';

comment on column public.employees.name is 'Имя';
comment on column public.employees.patronymic is 'Отчество';
comment on column public.employees.salary is 'Оклад';

alter table public.employees
    owner to postgres;

create sequence employee_seq
    as integer
    cache 10;

comment on sequence employee_seq is 'For employees.Id';

alter sequence employee_seq owner to postgres;

