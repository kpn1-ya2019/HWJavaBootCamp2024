create table if not exists public.paymenttypes
(
    id   integer not null
    constraint paymenttypes_pk
    primary key,
    name text
);

comment on table public.paymenttypes is 'Типы выплат';

comment on column public.paymenttypes.id is 'Идентификатор';
comment on column public.paymenttypes.name is 'Наименование';

alter table public.paymenttypes
    owner to postgres;

create sequence paymenttype_seq
    as integer
    cache 10;

comment on sequence paymenttype_seq is 'For paymenttypes.Id';

alter sequence paymenttype_seq owner to postgres;

