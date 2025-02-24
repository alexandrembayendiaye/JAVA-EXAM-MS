package com.groupeisi.matieres.mapper;

import com.groupeisi.matieres.dto.requests.MatiereDtoRequest;
import com.groupeisi.matieres.dto.responses.MatiereDtoResponse;
import com.groupeisi.matieres.entities.Matiere;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface MatiereMapper {
    // Mapping de MatiereDtoRequest vers Matiere
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "archive", target = "archive")
    @Mapping(source = "coursId", target = "cours.id") // Mapping de coursId vers cours.id
    Matiere toEntity(MatiereDtoRequest matiereDtoRequest);

    // Mapping de Matiere vers MatiereDtoResponse
    @Mapping(source = "cours.nom", target = "coursNom") // Mapping de cours.nom vers coursNom
    MatiereDtoResponse toDto(Matiere matiere);

    // Liste de Matiere vers Liste de MatiereDtoResponse
    List<MatiereDtoResponse> toDtoList(List<Matiere> matieres);

    // Liste de MatiereDtoRequest vers Liste de Matiere
    List<Matiere> toEntityList(List<MatiereDtoRequest> matiereDtoRequests);
}
