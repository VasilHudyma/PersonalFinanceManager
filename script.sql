create database finance_manager
    with owner postgres;

create table categories
(
    id           bigserial               not null
        constraint categories_pk
            primary key,
    name         varchar                 not null,
    description  varchar,
    created_date timestamp default now() not null,
    updated_date timestamp default now()
);

alter table categories
    owner to postgres;

create table operations
(
    id           bigserial not null
        constraint operations_pk
            primary key,
    name         varchar   not null,
    created_date timestamp default now(),
    updated_date timestamp default now()
);

alter table operations
    owner to postgres;

create table transactions
(
    id           bigserial not null
        constraint transactions_pk
            primary key,
    category_id  bigint    not null
        constraint fk
            references categories,
    operation_id bigint    not null
        constraint fk2
            references operations,
    sum          real      not null,
    description  varchar,
    created_date timestamp default now(),
    updated_date timestamp default now()
);

alter table transactions
    owner to postgres;


