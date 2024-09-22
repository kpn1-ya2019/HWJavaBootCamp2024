create table if not exists public.paymentorders
(
    id   integer not null
    constraint paymentorders_pk
    primary key,
    idtype integer
    constraint paymentorders_paymenttypes_id_fk
    references paymenttypes,
    iddepartment integer
    constraint paymentorders_departments_id_fk
    references departments,
    idemployee integer
    constraint paymentorders_employees_id_fk
    references employees,
    datepayment date,
    amount numeric

);

comment on table public.paymentorders is 'Платежные поручения перевода заработной платы (кому, сумма, дата перечисления)';

comment on column public.paymentorders.id is 'Идентификатор поручения';

comment on column public.paymentorders.idtype is 'Идентификатор типа выплаты';

comment on column public.paymentorders.iddepartment is 'Идентификатор отдела';

comment on column public.paymentorders.idemployee is 'Идентификатор сотрудника';

comment on column public.paymentorders.datepayment is 'Дата поручения';

comment on column public.paymentorders.amount is 'Сумма поручения';

    alter table public.paymentorders
    owner to postgres;

create sequence paymentorder_seq
    as integer
    cache 10;

comment on sequence paymentorder_seq is 'For paymentorders.Id';

alter sequence paymentorder_seq owner to postgres;

