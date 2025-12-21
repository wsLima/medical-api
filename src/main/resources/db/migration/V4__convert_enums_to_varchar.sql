BEGIN;

-- -------------------------------------------------------
-- PROFILES.role (user_role)
-- -------------------------------------------------------
ALTER TABLE profiles
ALTER COLUMN role TYPE VARCHAR(30)
    USING role::text;

ALTER TABLE profiles
    ALTER COLUMN role SET DEFAULT 'PATIENT';

-- -------------------------------------------------------
-- PROFESSIONALS.specialty (specialty)
-- -------------------------------------------------------
ALTER TABLE professionals
ALTER COLUMN specialty TYPE VARCHAR(40)
    USING specialty::text;

ALTER TABLE professionals
    ALTER COLUMN specialty SET DEFAULT 'GENERAL_PRACTICE';

-- -------------------------------------------------------
-- APPOINTMENTS.status (appointment_status)
-- -------------------------------------------------------
ALTER TABLE appointments
ALTER COLUMN status TYPE VARCHAR(30)
    USING status::text;

ALTER TABLE appointments
    ALTER COLUMN status SET DEFAULT 'SCHEDULED';

-- -------------------------------------------------------
-- DOCUMENTS.type (document_type)
-- -------------------------------------------------------
ALTER TABLE documents
ALTER COLUMN type TYPE VARCHAR(40)
    USING type::text;

-- -------------------------------------------------------
-- DROP ENUM TYPES
-- -------------------------------------------------------
DROP TYPE IF EXISTS user_role;
DROP TYPE IF EXISTS specialty;
DROP TYPE IF EXISTS appointment_status;
DROP TYPE IF EXISTS document_type;

COMMIT;