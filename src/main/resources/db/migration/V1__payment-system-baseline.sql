
create table if not exists ar_internal_metadata
(
    key varchar not null
        constraint ar_internal_metadata_pkey
        primary key,
    value varchar,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null
);

alter table ar_internal_metadata owner to postgres;

create table if not exists access_cards
(
    id bigserial not null
        constraint access_cards_pkey
        primary key,
    user_id integer,
    nickname varchar,
    rfid varchar,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null
);

alter table access_cards owner to postgres;

create index if not exists index_access_cards_on_user_id
    on access_cards (user_id);

create table if not exists access_requests
(
    id bigserial not null
        constraint access_requests_pkey
        primary key,
    user_id integer,
    device_id integer,
    is_successful boolean,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null
);

alter table access_requests owner to postgres;

create index if not exists index_access_requests_on_device_id
    on access_requests (device_id);

create index if not exists index_access_requests_on_user_id
    on access_requests (user_id);

create table if not exists devices
(
    id bigserial not null
        constraint devices_pkey
        primary key,
    name varchar,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null
);

alter table devices owner to postgres;

create table if not exists payments
(
    id bigserial not null
        constraint payments_pkey
        primary key,
    user_id integer,
    gc_reference varchar,
    amount integer,
    currency varchar,
    description varchar,
    status varchar,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    subscription_id integer
);

alter table payments owner to postgres;

create index if not exists index_payments_on_user_id
    on payments (user_id);

create table if not exists permissions
(
    id bigserial not null
        constraint permissions_pkey
        primary key,
    user_id integer,
    device_id integer,
    level integer,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null
);

alter table permissions owner to postgres;

create index if not exists index_permissions_on_device_id
    on permissions (device_id);

create index if not exists index_permissions_on_user_id
    on permissions (user_id);

create table if not exists subscriptions
(
    id bigserial not null
        constraint subscriptions_pkey
        primary key,
    user_id integer,
    gc_reference varchar,
    amount integer,
    currency varchar,
    day_of_month integer,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    paused_at timestamp,
    cancelled_at timestamp
);

alter table subscriptions owner to postgres;

create index if not exists index_subscriptions_on_user_id
    on subscriptions (user_id);

create table if not exists users
(
    id bigserial not null
        constraint users_pkey
        primary key,
    first_name varchar,
    last_name varchar,
    gc_reference varchar,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    email varchar default ''::character varying not null,
    encrypted_password varchar default ''::character varying not null,
    reset_password_token varchar,
    reset_password_sent_at timestamp,
    remember_created_at timestamp,
    sign_in_count integer default 0 not null,
    current_sign_in_at timestamp,
    last_sign_in_at timestamp,
    current_sign_in_ip varchar,
    last_sign_in_ip varchar,
    confirmation_token varchar,
    confirmed_at timestamp,
    confirmation_sent_at timestamp,
    unconfirmed_email varchar,
    failed_attempts integer default 0 not null,
    unlock_token varchar,
    locked_at timestamp,
    gc_mandate_reference varchar,
    provider varchar,
    uid varchar
);

alter table users owner to postgres;

create unique index if not exists  index_users_on_confirmation_token
    on users (confirmation_token);

create unique index if not exists  index_users_on_email
    on users (email);

create unique index if not exists  index_users_on_reset_password_token
    on users (reset_password_token);

create unique index if not exists  index_users_on_unlock_token
    on users (unlock_token);

