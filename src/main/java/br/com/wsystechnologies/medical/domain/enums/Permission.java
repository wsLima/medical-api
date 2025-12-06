package br.com.wsystechnologies.medical.domain.enums;

public enum Permission {
    // Pacientes
    PATIENT_CREATE,
    PATIENT_UPDATE,
    PATIENT_VIEW,

    // Agenda
    APPOINTMENT_CREATE,
    APPOINTMENT_UPDATE,
    APPOINTMENT_VIEW,

    // Prontuário
    MEDICAL_RECORD_CREATE,
    MEDICAL_RECORD_UPDATE,
    MEDICAL_RECORD_VIEW,

    // Evolução
    EVOLUTION_CREATE,
    EVOLUTION_VIEW,

    // Documentos
    DOCUMENT_UPLOAD,
    DOCUMENT_VIEW,

    // Financeiro
    FINANCE_VIEW,
    FINANCE_UPDATE,

    // Auditoria
    AUDIT_VIEW
}
