package com.groupeisi.cours.mapper;

import com.groupeisi.cours.dto.requests.CoursDtoRequest;
import com.groupeisi.cours.dto.responses.CoursDtoResponse;
import com.groupeisi.cours.entities.Cours;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CoursMapper {
    // Mapping de CoursDtoRequest vers Cours
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "archive", target = "archive")
    Cours toEntity(CoursDtoRequest coursDtoRequest);

    CoursDtoResponse toDto(Cours cours);

    // Liste de Cours vers Liste de CoursDtoResponse
    List<CoursDtoResponse> toDtoList(List<Cours> coursList);

    // Liste de CoursDtoRequest vers Liste de Cours
    List<Cours> toEntityList(List<CoursDtoRequest> coursDtoList);

}
