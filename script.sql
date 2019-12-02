create table categories
(
    id           bigserial               not null,
    name         varchar                 not null,
    description  varchar,
    created_date timestamp default now() not null,
    updated_date timestamp default now(),
    constraint categories_pk
        primary key (id)
);


create table operations
(
    id           bigserial not null,
    name         varchar   not null,
    created_date timestamp default now(),
    updated_date timestamp default now(),
    constraint operations_pk
        primary key (id)
);


create table users
(
    id           bigserial               not null,
    email        varchar                 not null,
    password     varchar                 not null,
    name         varchar                 not null,
    surname      varchar                 not null,
    phone        varchar                 not null,
    role         varchar                 not null,
    created_date timestamp default now() not null,
    updated_date timestamp default now() not null,
    constraint users_pk
        primary key (id)
);


create unique index users_email_uindex
    on users (email);

create table transactions
(
    id           bigserial not null,
    category_id  bigint    not null,
    operation_id bigint    not null,
    sum          real      not null,
    description  varchar,
    created_date timestamp default now(),
    updated_date timestamp default now(),
    user_id      bigint    not null,
    constraint transactions_pk
        primary key (id),
    constraint fk
        foreign key (category_id) references categories on DELETE CASCADE ,
    constraint fk2
        foreign key (operation_id) references operations on DELETE CASCADE ,
    constraint users_for_key
        foreign key (user_id) references users on DELETE CASCADE
 );



