package br.com.wsystechnologies.medical.application.dto.clinic;

import java.util.UUID;

public record ClinicResponse(
        UUID id,
        String name,
        String legalName,
        String cnpj,
        String phone,
        String email,
        String addressStreet,
        String addressNumber,
        String addressComplement,
        String addressNeighborhood,
        String addressCity,
        String addressState,
        String addressZipcode,
        boolean active
) {
}
