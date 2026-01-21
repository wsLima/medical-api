create table specialties
(
    id              uuid primary key      default gen_random_uuid(),
    tenant_id       uuid         not null,
    professional_id uuid         not null references professionals (id) on delete cascade,
    name            varchar(120) not null,
    description     text,
    created_at      timestamptz  not null default now(),
    updated_at      timestamptz  not null default now()
);

create index specialties_tenant_idx on specialties (tenant_id);
create index specialties_professional_idx on specialties (professional_id);
