create table if not exists public.departments
(
    id   integer not null
        constraint departments_pk
            primary key,
    code text,
    name text
);

comment on table public.departments is 'Отделы';

comment on column public.departments.code is 'Код отдела';

comment on column public.departments.name is 'Наименование';

alter table public.departments
    owner to postgres;

