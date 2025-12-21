create table prescriptions
(
    id              uuid primary key     default gen_random_uuid(),
    tenant_id       uuid        not null,
    clinic_id       uuid        not null references clinics (id) on delete cascade,
    patient_id      uuid        not null references patients (id) on delete cascade,
    professional_id uuid        not null references professionals (id) on delete cascade,
    appointment_id  uuid        references appointments (id) on delete set null,
    type            varchar(50) not null, -- tipo livre: MEDICAMENTO, EXAME, ORIENTAÇÃO
    description     text        not null, -- nome do medicamento ou exame
    dosage          varchar(120),         -- posologia
    frequency       varchar(120),         -- frequência
    duration        varchar(120),         -- tempo de uso
    notes           text,                 -- observações adicionais
    created_at      timestamptz not null default now(),
    updated_at      timestamptz not null default now()
);

create index prescriptions_patient_idx on prescriptions (patient_id);
create index prescriptions_professional_idx on prescriptions (professional_id);
create index prescriptions_clinic_idx on prescriptions (clinic_id);
