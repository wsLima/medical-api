-- Inserir clínica piloto
INSERT INTO clinics (id, name, legal_name, cnpj, phone,  email, street, number, complement, neighborhood, city, state, zip_code, active, created_at, updated_at)
VALUES (
        gen_random_uuid(), 'Clínica Piloto', 'Clínica Piloto LTDA',
        '00.000.000/0001-00', '(11) 99999-0000', 'contato@clinicadopiloto.com', 'Av. Paulista',
        '1000', 'Conj. 1205', 'Bela Vista', 'São Paulo', 'SP', '01310-100',
        TRUE, NOW(), NOW());

-- Inserir usuário admin
insert into users (id, email, password, enabled, created_at, updated_at)
values (gen_random_uuid(),
        'admin@clinic.com',
           -- senha: admin123 (hash gerado via bcrypt, substitua pelo seu encoder)
        '$2a$12$tuf//r8vl41Ks9vKwUh72em.smKa2micT4dcH8FU9b5w3q4SBCriy',
        true, NOW(), NOW());

-- Vincular perfil ao usuário admin
insert into profiles (id, clinic_id, full_name, role, is_active, created_at, updated_at)
values ((select id from users where email = 'admin@clinic.com'),
        (select id from clinics where name = 'Clínica Piloto'),
        'Administrador Piloto',
        'CLINIC_ADMIN',
        true,
        now(),
        now());
