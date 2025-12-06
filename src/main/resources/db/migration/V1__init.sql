-- Extensões úteis
create
extension if not exists "uuid-ossp";
create
extension if not exists "pgcrypto";

-- ENUMS
create type user_role as enum (
  'CLINIC_ADMIN',
  'MEDICO',
  'ENFERMEIRA',
  'ATENDENTE',
  'RECEPCIONISTA',
  'FINANCE',
  'ASSISTANT',
  'PACIENTE'
);

create type appointment_status as enum (
  'SCHEDULED',
  'CONFIRMED',
  'COMPLETED',
  'NO_SHOW',
  'CANCELLED'
);

create type document_type as enum (
  'EXAM',
  'REPORT',
  'CONTRACT',
  'OTHER'
);

create type specialty as enum (
  'CARDIOLOGY',
  'DERMATOLOGY',
  'PEDIATRICS',
  'NEUROLOGY',
  'ORTHOPEDICS',
  'PSYCHIATRY',
  'GYNECOLOGY',
  'GENERAL_PRACTICE'
);

-------------------------------------------------------
-- CLINICS
-------------------------------------------------------
create table clinics
(
    id           uuid primary key      default gen_random_uuid(),
    name         varchar(120) not null,
    legal_name   varchar(120) not null,
    cnpj         varchar(18)  not null unique,
    phone        varchar(20),
    email        varchar(120),
    street       varchar(120),
    number       varchar(20),
    complement   varchar(120),
    neighborhood varchar(120),
    city         varchar(120),
    state        varchar(2),
    zip_code      varchar(12),
    active       boolean      not null default true,
    created_at   timestamptz  not null default now(),
    updated_at   timestamptz  not null default now(),

    constraint uk_clinic_cnpj unique (cnpj)
);

create index clinics_name_idx on clinics (name);

-------------------------------------------------------
-- USERS
-------------------------------------------------------
create table users
(
    id       uuid primary key default gen_random_uuid(),
    email    text    not null unique,
    password text    not null,
    enabled  boolean not null default true,
    created_at   timestamptz  not null default now(),
    updated_at   timestamptz  not null default now()
);

-------------------------------------------------------
-- PROFILES
-------------------------------------------------------
create table profiles
(
    id         uuid primary key     default gen_random_uuid(),
    clinic_id  uuid        not null references clinics (id) on delete cascade,
    full_name  text        not null,
    role       user_role   not null default 'PACIENTE',
    is_active  boolean     not null default true,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),

    constraint fk_profiles_user
        foreign key (id) references users (id) on delete cascade
);

create index profiles_clinic_id_idx on profiles (clinic_id);
create index profiles_role_idx on profiles (role);

-------------------------------------------------------
-- PROFESSIONALS
-------------------------------------------------------
create table professionals
(
    id             uuid primary key     default gen_random_uuid(),

    constraint fk_professional_profile
        foreign key (id) references profiles (id) on delete cascade,

    clinic_id      uuid        not null references clinics (id) on delete cascade,
    crm            text        not null unique,
    specialty      specialty   not null default 'GENERAL_PRACTICE',
    available_from time,
    available_to   time,
    active         boolean     not null default true,

    created_at     timestamptz not null default now(),
    updated_at     timestamptz not null default now()
);

-------------------------------------------------------
-- DOCTOR_AVAILABLE_DAYS (ElementCollection)
-------------------------------------------------------
create table doctor_available_days
(
    doctor_id uuid not null references professionals (id) on delete cascade,
    day       int  not null, -- 1=Mon ... 7=Sun

    constraint pk_doctor_available_days primary key (doctor_id, day)
);


-------------------------------------------------------
-- PATIENTS
-------------------------------------------------------
create table patients
(
    id                   uuid primary key     default gen_random_uuid(),
    clinic_id            uuid        not null references clinics (id) on delete cascade,
    full_name            text        not null,
    date_of_birth        date,
    document_id          text,
    phone                text,
    email                text,
    street       varchar(120),
    number       varchar(20),
    complement   varchar(120),
    neighborhood varchar(120),
    city         varchar(120),
    state        varchar(2),
    zip_code      varchar(12),
    notes                text,
    created_by           uuid references profiles (id),
    created_at           timestamptz not null default now(),
    updated_at           timestamptz not null default now()
);

create index patients_clinic_id_idx on patients (clinic_id);
create index patients_name_idx on patients (full_name);

-------------------------------------------------------
-- APPOINTMENTS
-------------------------------------------------------
create table appointments
(
    id              uuid primary key            default gen_random_uuid(),
    clinic_id       uuid               not null references clinics (id) on delete cascade,
    patient_id      uuid               not null references patients (id) on delete restrict,
    professional_id uuid               not null references professionals (id) on delete restrict,
    starts_at       timestamptz        not null,
    ends_at         timestamptz        not null,
    status          appointment_status not null default 'SCHEDULED',
    notes           text,
    created_by      uuid references profiles (id),
    created_at      timestamptz        not null default now(),
    updated_at      timestamptz        not null default now()
);

create index appointments_clinic_id_idx on appointments (clinic_id);
create index appointments_professional_id_idx on appointments (professional_id);
create index appointments_patient_id_idx on appointments (patient_id);
create index appointments_starts_at_idx on appointments (starts_at);

-------------------------------------------------------
-- MEDICAL_RECORDS
-------------------------------------------------------
create table medical_records
(
    id         uuid primary key     default gen_random_uuid(),
    clinic_id  uuid        not null references clinics (id) on delete cascade,
    patient_id uuid        not null references patients (id) on delete cascade,
    created_by uuid references professionals (id),
    main_issue text,
    anamnesis  jsonb,
    notes      text,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);

create index medical_records_clinic_id_idx on medical_records (clinic_id);
create index medical_records_patient_id_idx on medical_records (patient_id);

-------------------------------------------------------
-- EVOLUTIONS
-------------------------------------------------------
create table evolutions
(
    id              uuid primary key     default gen_random_uuid(),
    clinic_id       uuid        not null references clinics (id) on delete cascade,
    patient_id      uuid        not null references patients (id) on delete cascade,
    professional_id uuid        not null references professionals (id),
    appointment_id  uuid references appointments (id),
    notes           text,
    structured_data jsonb,
    created_at      timestamptz not null default now(),
    updated_at      timestamptz not null default now()
);

create index evolutions_clinic_id_idx on evolutions (clinic_id);
create index evolutions_patient_id_idx on evolutions (patient_id);
create index evolutions_professional_id_idx on evolutions (professional_id);

-------------------------------------------------------
-- DOCUMENTS
-------------------------------------------------------
create table documents
(
    id           uuid primary key       default gen_random_uuid(),
    clinic_id    uuid          not null references clinics (id) on delete cascade,
    patient_id   uuid          references patients (id) on delete set null,
    uploaded_by  uuid references profiles (id),
    type         document_type not null default 'OTHER',
    name         text          not null,
    storage_path text          not null,
    mime_type    text,
    size_bytes   bigint,
    created_at   timestamptz   not null default now()
);

create index documents_clinic_id_idx on documents (clinic_id);
create index documents_patient_id_idx on documents (patient_id);

-------------------------------------------------------
-- AUDIT_LOGS
-------------------------------------------------------
create table audit_logs
(
    id          bigserial primary key,
    clinic_id   uuid references clinics (id),
    user_id     uuid references profiles (id),
    entity_type text        not null,
    entity_id   uuid,
    action      text        not null,
    details     jsonb,
    created_at  timestamptz not null default now()
);

create index audit_logs_clinic_id_idx on audit_logs (clinic_id);
create index audit_logs_user_id_idx on audit_logs (user_id);
create index audit_logs_entity_idx on audit_logs (entity_type, entity_id);
