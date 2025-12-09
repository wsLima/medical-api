-- =======================================================
-- V1__seed.sql - Seed inicial com tenant
-- =======================================================

-- Tenant inicial
-- -------------------------------------------------------
insert into clinics (id, tenant_id, name, legal_name, cnpj, phone, email, active)
values (
           gen_random_uuid(),
           '11111111-1111-1111-1111-111111111111',
           'Clínica Bem Estar',
           'Clínica Bem Estar Ltda',
           '12.345.678/0001-99',
           '(92) 3300-1000',
           'contato@bemestar.com',
           true
       );

-- -------------------------------------------------------
-- Endereços
-- -------------------------------------------------------
insert into addresses (id, street, number, neighborhood, city, state, zip_code, type)
values
    (gen_random_uuid(), 'Av. Djalma Batista', '1000', 'Chapada', 'Manaus', 'AM', '69050-010', 'COMMERCIAL'),
    (gen_random_uuid(), 'Rua das Flores', '200', 'Centro', 'Manaus', 'AM', '69010-020', 'RESIDENTIAL');

-- -------------------------------------------------------
-- Persons
-- -------------------------------------------------------
insert into persons (id, tenant_id, full_name, cpf, birth_date, gender, phone, email)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'Dr. João Silva', '12345678901', '1980-05-10', 'M', '(92) 99111-1111', 'joao.silva@bemestar.com'),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', 'Maria Oliveira', '98765432100', '1990-08-20', 'F', '(92) 99222-2222', 'maria.oliveira@gmail.com');

-- -------------------------------------------------------
-- Accounts
-- -------------------------------------------------------
insert into accounts (id, tenant_id, clinic_id, person_id, email, password, enabled)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), (select id from persons where full_name='Dr. João Silva'), 'joao.silva@bemestar.com', '$2a$12$6Tcf3m2.72PLoCzeDG1sQuxLOrENlPn09DaPhTvlzjD9uvHdm2eUS', true),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), (select id from persons where full_name='Maria Oliveira'), 'maria.oliveira@gmail.com', '$2a$12$6Tcf3m2.72PLoCzeDG1sQuxLOrENlPn09DaPhTvlzjD9uvHdm2eUS', true);

-- -------------------------------------------------------
-- Profiles
-- -------------------------------------------------------
insert into profiles (id, tenant_id, clinic_id, account_id, role, is_active)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), (select id from accounts where email='joao.silva@bemestar.com'), 'MEDICO', true),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), (select id from accounts where email='maria.oliveira@gmail.com'), 'PACIENTE', true);

-- -------------------------------------------------------
-- Professionals
-- -------------------------------------------------------
insert into professionals (id, tenant_id, profile_id, clinic_id, crm, specialty, active)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from profiles where role='MEDICO'), (select id from clinics where name='Clínica Bem Estar'), 'CRM-AM-12345', 'CARDIOLOGY', true);

-- -------------------------------------------------------
-- Patients
-- -------------------------------------------------------
insert into patients (id, tenant_id, clinic_id, full_name, date_of_birth, phone, email, created_by_id, active)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), 'Maria Oliveira', '1990-08-20', '(92) 99222-2222', 'maria.oliveira@gmail.com', (select id from profiles where role='MEDICO'), true);

-- -------------------------------------------------------
-- Appointments
-- -------------------------------------------------------
insert into appointments (id, tenant_id, clinic_id, patient_id, professional_id, starts_at, ends_at, status, notes)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'),
     (select id from patients where full_name='Maria Oliveira'),
     (select id from professionals where crm='CRM-AM-12345'),
     '2025-12-10 09:00:00', '2025-12-10 09:30:00', 'SCHEDULED', 'Consulta inicial');

-- -------------------------------------------------------
-- Medical Records
-- -------------------------------------------------------
insert into medical_records (id, tenant_id, clinic_id, patient_id, created_by_id, main_issue, anamnesis, notes)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'),
     (select id from patients where full_name='Maria Oliveira'),
     (select id from professionals where crm='CRM-AM-12345'),
     'Dor no peito', '{"historia":"Paciente relata dor no peito há 2 semanas"}', 'Solicitado exame de ECG');

-- -------------------------------------------------------
-- Evolutions
-- -------------------------------------------------------
insert into evolutions (id, tenant_id, clinic_id, patient_id, professional_id, appointment_id, notes, structured_data)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'),
     (select id from patients where full_name='Maria Oliveira'),
     (select id from professionals where crm='CRM-AM-12345'),
     (select id from appointments where notes='Consulta inicial'),
     'Paciente apresentou melhora após uso de medicação',
     '{"pressao":"120/80","frequencia":"72"}');

-- -------------------------------------------------------
-- Documents
-- -------------------------------------------------------
insert into documents (id, tenant_id, clinic_id, patient_id, appointment_id, type, name, storage_path)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'),
     (select id from patients where full_name='Maria Oliveira'),
     (select id from appointments where notes='Consulta inicial'),
     'EXAM', 'ECG_2025.pdf', '/storage/docs/ecg_2025.pdf');

-- -------------------------------------------------------
-- Services Provided
-- -------------------------------------------------------
insert into services_provided (id, tenant_id, clinic_id, name, description, price, active)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), 'Consulta Cardiologia', 'Consulta completa de cardiologia', 250.0, true),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), 'Consulta Dermatologia', 'Consulta completa de dermatologia', 180.0, true),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), 'Consulta Pediatria', 'Consulta pediátrica com avaliação completa', 150.0, true),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), 'Consulta Neurologia', 'Avaliação neurológica detalhada', 300.0, true),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), 'Consulta Ortopedia', 'Avaliação ortopédica com exames complementares', 200.0, true),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), 'Consulta Psiquiatria', 'Consulta psiquiátrica com acompanhamento', 220.0, true),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), 'Consulta Ginecologia', 'Consulta ginecológica completa', 210.0, true),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), 'Exame de ECG', 'Exame de eletrocardiograma', 120.0, true),
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), 'Exame de Sangue', 'Painel completo de exames de sangue', 100.0, true);

-- -------------------------------------------------------
-- Audit Logs
-- -------------------------------------------------------
insert into audit_logs (id, tenant_id, clinic_id, user_id, entity_type, entity_id, action, details)
values
    (gen_random_uuid(), '11111111-1111-1111-1111-111111111111', (select id from clinics where name='Clínica Bem Estar'), (select id from profiles where role='MEDICO'), 'Patient', (select id from patients where full_name='Maria Oliveira'), 'CREATE', '{"field":"full_name","value":"Maria Oliveira"}');
