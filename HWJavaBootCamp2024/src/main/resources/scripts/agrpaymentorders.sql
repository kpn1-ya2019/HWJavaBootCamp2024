
create table if not exists public.agrpaymentorders
(
    id   integer not null
    constraint agrpaymentorders_pk
    primary key,
    paymenttypename text,
    departmentcode text,
    departmentname text,
    employeesurname text,
    employeename text,
    employeepatronymic text,
    amountpaymentorder numeric
);

comment on table public.agrpaymentorders is 'Агрегат зарплаты платы (тип выплаты, кому, сумма)';

comment on column public.agrpaymentorders.id is 'Идентификатор';
comment on column public.agrpaymentorders.paymenttypename is 'Наименование типа оплаты';
comment on column public.agrpaymentorders.departmentcode is 'Код отдела';
comment on column public.agrpaymentorders.departmentname is 'Наименование отдела';
comment on column public.agrpaymentorders.employeesurname    is 'Сотрудник:Фамилия';
comment on column public.agrpaymentorders.employeename       is 'Сотрудник:Имя';
comment on column public.agrpaymentorders.employeepatronymic is 'Сотрудник:Отчество';
comment on column public.agrpaymentorders.amountpaymentorder is 'Сотрудник:сумма выплаты';

alter table public.agrpaymentorders
    owner to postgres;

create sequence agrpaymentorder_seq
    as integer
    cache 10;

comment on sequence agrpaymentorder_seq is 'For agrpaymentorders.Id';

alter sequence agrpaymentorder_seq owner to postgres;
