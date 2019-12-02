create table categories
(
	id bigserial not null
		constraint categories_pk
			primary key,
	name varchar not null,
	description varchar,
	created_date timestamp default now() not null,
	updated_date timestamp default now()
);

alter table categories owner to postgres;

