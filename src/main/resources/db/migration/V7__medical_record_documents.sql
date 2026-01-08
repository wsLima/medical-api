-- -------------------------------------------------------
-- Tabela: medical_record_documents
-- -------------------------------------------------------
create table if not exists medical_record_documents
(
    id uuid primary key default gen_random_uuid(),
    tenant_id uuid not null,
    medical_record_id uuid not null,
    uploaded_by_id uuid not null,
    file_name varchar(200) not null,
    mime_type varchar(120) not null,
    size_bytes bigint not null,
    storage_path varchar(255) not null,
    description varchar(255),
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),

    -- -----------------------------
    -- Foreign Keys
    -- -----------------------------
    constraint fk_medical_record_documents_medical_record
    foreign key(medical_record_id)
    references medical_records(id)
    on delete cascade,
    constraint fk_medical_record_documents_uploaded_by
    foreign key(uploaded_by_id)
    references profiles(id)
);

-- -------------------------------------------------------
-- Índices
-- -------------------------------------------------------
create index if not exists idx_medical_record_documents_record
    on medical_record_documents (medical_record_id);

create index if not exists idx_medical_record_documents_tenant
    on medical_record_documents (tenant_id);