-- =======================================================
-- V1__init.sql - Banco de dados completo com multi-tenant
-- =======================================================

-- -------------------------------------------------------
-- Extensões necessárias
-- -------------------------------------------------------
create extension if not exists "uuid-ossp";
create extension if not exists "pgcrypto";

-- -------------------------------------------------------
-- ADDRESSES
-- -------------------------------------------------------
create table addresses
(
    id           uuid primary key      default gen_random_uuid(),
    street       varchar(120) not null,
    number       varchar(20),
    complement   varchar(120),
    neighborhood varchar(120),
    city         varchar(120),
    state        varchar(2) check (char_length(state) = 2),
    zip_code     varchar(12),
    type         varchar(30)  not null default 'RESIDENTIAL',
    created_at   timestamptz  not null default now(),
    updated_at   timestamptz  not null default now()
);
create index addresses_city_idx on addresses (city);

-- -------------------------------------------------------
-- CLINICS
-- -------------------------------------------------------
create table clinics
(
    id         uuid primary key      default gen_random_uuid(),
    tenant_id  uuid not null,
    name       varchar(120) not null,
    legal_name varchar(120) not null,
    cnpj       varchar(18)  not null unique,
    phone      varchar(20),
    email      varchar(120),
    address_id uuid references addresses (id) on delete set null,
    active     boolean not null default true,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create index clinics_tenant_id_idx on clinics (tenant_id);
create index clinics_name_idx on clinics (name);

-- -------------------------------------------------------
-- PERSONS
-- -------------------------------------------------------
create table persons
(
    id         uuid primary key default gen_random_uuid(),
    tenant_id  uuid not null,
    full_name  varchar(120) not null,
    cpf        varchar(14) unique,
    rg         varchar(20),
    birth_date date,
    gender     varchar(10),
    phone      varchar(20),
    email      varchar(120),
    address_id uuid references addresses (id) on delete set null,
    active     boolean not null default true,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create index persons_tenant_id_idx on persons (tenant_id);
create index persons_name_idx on persons (full_name);
create index persons_cpf_idx on persons (cpf);

-- -------------------------------------------------------
-- ACCOUNTS
-- -------------------------------------------------------
create table accounts
(
    id         uuid primary key default gen_random_uuid(),
    tenant_id  uuid not null,
    clinic_id  uuid not null references clinics (id) on delete cascade,
    person_id  uuid not null references persons (id) on delete cascade,
    address_id uuid references addresses (id) on delete set null,
    email      varchar(120) not null unique,
    password   varchar(120) not null,
    enabled    boolean not null default true,
    last_login timestamptz,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create index accounts_tenant_id_idx on accounts (tenant_id);
create index accounts_clinic_id_idx on accounts (clinic_id);
create index accounts_person_id_idx on accounts (person_id);

-- -------------------------------------------------------
-- PROFILES
-- -------------------------------------------------------
create table profiles
(
    id         uuid primary key default gen_random_uuid(),
    tenant_id  uuid not null,
    clinic_id  uuid not null references clinics (id) on delete cascade,
    account_id uuid not null references accounts (id) on delete cascade,
    role       user_role not null default 'PACIENTE',
    is_active  boolean not null default true,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create index profiles_tenant_id_idx on profiles (tenant_id);
create index profiles_clinic_id_idx on profiles (clinic_id);
create index profiles_role_idx on profiles (role);

-- -------------------------------------------------------
-- PROFESSIONALS
-- -------------------------------------------------------
create table professionals
(
    id          uuid primary key default gen_random_uuid(),
    tenant_id   uuid not null,
    profile_id  uuid not null references profiles (id) on delete cascade,
    clinic_id   uuid not null references clinics (id) on delete cascade,
    crm         varchar(30) not null unique,
    specialty   specialty not null default 'GENERAL_PRACTICE',
    available_from time,
    available_to   time,
    active      boolean not null default true,
    created_at  timestamptz not null default now(),
    updated_at  timestamptz not null default now()
);
create index professionals_tenant_id_idx on professionals (tenant_id);
create index professionals_profile_id_idx on professionals (profile_id);
create index professionals_clinic_id_idx on professionals (clinic_id);

-- -------------------------------------------------------
-- DOCTOR_AVAILABLE_DAYS
-- -------------------------------------------------------
create table doctor_available_days
(
    id uuid primary key default gen_random_uuid(),
    doctor_id uuid not null references professionals (id) on delete cascade,
    day int not null check (day between 1 and 7)
    );
create unique index uq_doctor_day on doctor_available_days(doctor_id, day);
-- -------------------------------------------------------
-- PATIENTS
-- -------------------------------------------------------
create table patients
(
    id          uuid primary key default gen_random_uuid(),
    tenant_id   uuid not null,
    clinic_id   uuid not null references clinics (id) on delete cascade,
    full_name   varchar(120) not null,
    date_of_birth date,
    document_id varchar(20),
    phone       varchar(20),
    email       varchar(120),
    address_id  uuid references addresses (id) on delete set null,
    notes       text,
    active      boolean not null default true,
    created_by_id uuid references profiles (id),
    created_at  timestamptz not null default now(),
    updated_at  timestamptz not null default now()
);
create index patients_tenant_id_idx on patients (tenant_id);
create index patients_clinic_id_idx on patients (clinic_id);
create index patients_name_idx on patients (full_name);

-- -------------------------------------------------------
-- SERVICES_PROVIDED
-- -------------------------------------------------------
create table services_provided
(
    id uuid primary key default gen_random_uuid(),
    tenant_id uuid not null,
    clinic_id uuid not null references clinics (id) on delete cascade,
    name varchar(120) not null,
    description varchar(500),
    price double precision not null,
    active boolean not null default true,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create index services_provided_tenant_id_idx on services_provided (tenant_id);
create index services_provided_clinic_id_idx on services_provided (clinic_id);

-- -------------------------------------------------------
-- APPOINTMENTS
-- -------------------------------------------------------
create table appointments
(
    id uuid primary key default gen_random_uuid(),
    tenant_id uuid not null,
    clinic_id uuid not null references clinics (id) on delete cascade,
    patient_id uuid not null references patients (id) on delete restrict,
    professional_id uuid not null references professionals (id) on delete restrict,
    service_provided_id uuid references services_provided(id) on delete restrict,
    starts_at timestamptz not null,
    ends_at timestamptz not null,
    status appointment_status not null default 'SCHEDULED',
    notes text,
    created_by_id uuid references profiles (id),
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    constraint chk_appointment_time check (ends_at > starts_at)
);
create index appointments_tenant_id_idx on appointments (tenant_id);
create index appointments_schedule_idx on appointments (professional_id, starts_at);

-- -------------------------------------------------------
-- MEDICAL_RECORDS
-- -------------------------------------------------------
create table medical_records
(
    id uuid primary key default gen_random_uuid(),
    tenant_id uuid not null,
    clinic_id uuid not null references clinics (id) on delete cascade,
    patient_id uuid not null references patients (id) on delete cascade,
    created_by_id uuid references professionals (id),
    main_issue varchar(255),
    anamnesis jsonb,
    notes text,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create index medical_records_tenant_id_idx on medical_records (tenant_id);
create index medical_records_clinic_id_idx on medical_records (clinic_id);
create index medical_records_patient_id_idx on medical_records (patient_id);

-- -------------------------------------------------------
-- EVOLUTIONS
-- -------------------------------------------------------
create table evolutions
(
    id uuid primary key default gen_random_uuid(),
    tenant_id uuid not null,
    clinic_id uuid not null references clinics (id) on delete cascade,
    patient_id uuid not null references patients (id) on delete cascade,
    professional_id uuid not null references professionals (id),
    appointment_id uuid references appointments (id),
    notes text,
    structured_data jsonb,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create index evolutions_tenant_id_idx on evolutions (tenant_id);
create index evolutions_clinic_id_idx on evolutions (clinic_id);
create index evolutions_patient_id_idx on evolutions (patient_id);
create index evolutions_professional_id_idx on evolutions (professional_id);

-- -------------------------------------------------------
-- DOCUMENTS
-- -------------------------------------------------------
create table documents
(
    id uuid primary key default gen_random_uuid(),
    tenant_id uuid not null,
    clinic_id uuid not null references clinics (id) on delete cascade,
    patient_id uuid not null references patients (id) on delete cascade,
    appointment_id uuid references appointments (id) on delete set null,
    type document_type not null,
    name varchar(180) not null,
    description varchar(500),
    storage_path varchar(255) not null,
    created_by_id uuid references profiles (id) on delete set null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create index documents_tenant_id_idx on documents (tenant_id);
create index documents_clinic_id_idx on documents (clinic_id);
create index documents_patient_id_idx on documents (patient_id);
create index documents_appointment_id_idx on documents (appointment_id);
create index documents_created_by_id_idx on documents (created_by_id);

-- -------------------------------------------------------
-- REFRESH_TOKENS
-- -------------------------------------------------------
create table refresh_tokens
(
    id uuid primary key default gen_random_uuid(),
    token varchar(255) not null unique,
    account_id uuid not null references accounts (id),
    expiry_date timestamptz not null
);
create index refresh_tokens_account_id_idx on refresh_tokens (account_id);

-- -------------------------------------------------------
-- AUDIT_LOGS
-- -------------------------------------------------------
create table audit_logs
(
    id uuid primary key default gen_random_uuid(),
    tenant_id uuid not null,
    clinic_id uuid not null references clinics (id) on delete cascade,
    user_id uuid references profiles (id) on delete set null,
    entity_type varchar(80) not null,
    entity_id uuid,
    action varchar(40) not null,
    details jsonb,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create index audit_logs_tenant_id_idx on audit_logs (tenant_id);
create index audit_logs_clinic_id_idx on audit_logs (clinic_id);
create index audit_logs_user_id_idx on audit_logs (user_id);
create index audit_logs_entity_idx on audit_logs (entity_type, entity_id);
create index audit_logs_created_at_idx on audit_logs (created_at);
