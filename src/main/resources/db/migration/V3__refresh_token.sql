create table refresh_tokens
(
    id          uuid primary key default gen_random_uuid(),
    token       text        not null unique,
    user_id     uuid        not null references users (id) on delete cascade,
    expiry_date timestamptz not null
);

create index refresh_tokens_user_id_idx on refresh_tokens (user_id);