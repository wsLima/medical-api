create table clinic_settings
(
    id          uuid primary key      default gen_random_uuid(),
    clinic_id   uuid         not null references clinics (id) on delete cascade,
    key         varchar(100) not null,
    value       varchar(500) not null,
    type        varchar(20)  not null default 'STRING', -- STRING, INT, BOOL, JSON
    description varchar(255),
    created_at  timestamptz  not null default now(),
    updated_at  timestamptz  not null default now(),
    constraint uq_clinic_settings unique (clinic_id, key)
);

create index clinic_settings_clinic_id_idx on clinic_settings (clinic_id);
ó