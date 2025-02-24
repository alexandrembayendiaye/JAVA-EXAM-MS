package com.groupeisi.enseignants.mapper;

import com.groupeisi.enseignants.dto.requests.EnseignantDtoRequest;
import com.groupeisi.enseignants.dto.responses.EnseignantDtoResponse;
import com.groupeisi.enseignants.entities.Enseignant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnseignantMapper {
    // Mapping de EnseignantDtoRequest vers Enseignant
    @Mapping(source = "prenom", target = "prenom")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "emailPro", target = "emailPro")
    @Mapping(source = "numeroTelephone", target = "numeroTelephone")
    @Mapping(source = "adresse", target = "adresse")
    @Mapping(source = "archive", target = "archive")
    Enseignant toEntity(EnseignantDtoRequest enseignantDtoRequest);

    // Mapping de Enseignant vers EnseignantDtoResponse
    EnseignantDtoResponse toDto(Enseignant enseignant);

    // Liste de Enseignant vers Liste de EnseignantDtoResponse
    List<EnseignantDtoResponse> toDtoList(List<Enseignant> enseignants);

    // Liste de EnseignantDtoRequest vers Liste de Enseignant
    List<Enseignant> toEntityList(List<EnseignantDtoRequest> enseignantDtoRequests);
}
