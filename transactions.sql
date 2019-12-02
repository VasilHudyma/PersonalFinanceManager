create table transactions
(
	id bigserial not null
		constraint transactions_pk
			primary key,
	category_id bigint not null
		constraint fk
			references categories,
	operation_id bigint not null
		constraint fk2
			references operations,
	sum real not null,
	description varchar,
	created_date timestamp default now(),
	updated_date timestamp default now()
);

alter table transactions owner to postgres;

