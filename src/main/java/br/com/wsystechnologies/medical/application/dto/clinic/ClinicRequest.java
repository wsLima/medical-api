package br.com.wsystechnologies.medical.application.dto.clinic;

public record ClinicRequest(
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
