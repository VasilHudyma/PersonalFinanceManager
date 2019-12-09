create table operations
(
	id bigserial not null
		constraint operations_pk
			primary key,
	name varchar not null,
	created_date timestamp default now(),
	updated_date timestamp default now()
);

alter table operations owner to postgres;

