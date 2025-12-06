package br.com.wsystechnologies.medical.infrastructure.security;

import br.com.wsystechnologies.medical.domain.enums.Permission;
import br.com.wsystechnologies.medical.domain.enums.Role;

import java.util.Map;
import java.util.Set;

public class RolePermissions {

    private static final Map<Role, Set<Permission>> permissions = Map.of(

            Role.SYSTEM_ADMIN, Set.of(Permission.values()), // acesso total

            Role.CLINIC_ADMIN, Set.of(Permission.values()), // acesso total

            Role.MEDICO, Set.of(
                    Permission.PATIENT_VIEW,
                    Permission.APPOINTMENT_VIEW,
                    Permission.MEDICAL_RECORD_CREATE,
                    Permission.MEDICAL_RECORD_UPDATE,
                    Permission.MEDICAL_RECORD_VIEW,
                    Permission.EVOLUTION_CREATE,
                    Permission.EVOLUTION_VIEW,
                    Permission.DOCUMENT_UPLOAD,
                    Permission.DOCUMENT_VIEW,
                    Permission.PATIENT_CREATE,
                    Permission.PATIENT_UPDATE,
                    Permission.APPOINTMENT_CREATE,
                    Permission.APPOINTMENT_UPDATE
            ),

            Role.ENFERMEIRA, Set.of(
                    Permission.PATIENT_VIEW,
                    Permission.APPOINTMENT_VIEW,
                    Permission.EVOLUTION_CREATE,
                    Permission.EVOLUTION_VIEW
            ),

            Role.ATENDENTE, Set.of(
                    Permission.PATIENT_CREATE,
                    Permission.PATIENT_UPDATE,
                    Permission.PATIENT_VIEW,
                    Permission.APPOINTMENT_CREATE,
                    Permission.APPOINTMENT_UPDATE,
                    Permission.APPOINTMENT_VIEW
            ),

            Role.RECEPCIONISTA, Set.of(
                    Permission.PATIENT_CREATE,
                    Permission.PATIENT_UPDATE,
                    Permission.PATIENT_VIEW,
                    Permission.APPOINTMENT_CREATE,
                    Permission.APPOINTMENT_UPDATE,
                    Permission.APPOINTMENT_VIEW
            ),

            Role.FINANCE, Set.of(
                    Permission.FINANCE_VIEW,
                    Permission.FINANCE_UPDATE
            ),

            Role.ASSISTANT, Set.of(
                    Permission.PATIENT_VIEW,
                    Permission.APPOINTMENT_VIEW,
                    Permission.MEDICAL_RECORD_VIEW,
                    Permission.EVOLUTION_VIEW,
                    Permission.DOCUMENT_VIEW
            ),

            Role.PACIENTE, Set.of(
                    Permission.PATIENT_VIEW,
                    Permission.APPOINTMENT_VIEW,
                    Permission.DOCUMENT_VIEW
            )
    );

    public static Set<Permission> getPermissions(Role role) {
        return permissions.getOrDefault(role, Set.of());
    }
}
