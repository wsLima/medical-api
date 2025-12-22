-- MEDICAL_RECORDS
ALTER TABLE medical_records
ALTER COLUMN anamnesis TYPE TEXT
    USING anamnesis::TEXT;

-- EVOLUTIONS
ALTER TABLE evolutions
ALTER COLUMN structured_data TYPE TEXT
    USING structured_data::TEXT;

-- AUDIT_LOGS
ALTER TABLE audit_logs
ALTER COLUMN details TYPE TEXT
    USING details::TEXT;
