package com.groupeisi.sessions.mapper;

import com.groupeisi.cours.repository.CoursRepository;
import com.groupeisi.sessions.dto.requests.SessionDtoRequest;
import com.groupeisi.sessions.dto.responses.SessionDtoResponse;
import com.groupeisi.sessions.entities.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    // Mapping de SessionDtoRequest vers Session
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "dateDebut", target = "dateDebut")
    @Mapping(source = "dateFin", target = "dateFin")
    @Mapping(source = "archive", target = "archive")
    @Mapping(source = "coursId", target = "cours.id") // Mapping de coursId vers cours.id
    Session toEntity(SessionDtoRequest sessionDtoRequest);

    // Mapping de Session vers SessionDtoResponse
    @Mapping(source = "cours.nom", target = "coursNom") // Mapping de cours.nom vers coursNom
    SessionDtoResponse toDto(Session session);

    // Liste de Session vers Liste de SessionDtoResponse
    List<SessionDtoResponse> toDtoList(List<Session> sessions);

    // Liste de SessionDtoRequest vers Liste de Session
    List<Session> toEntityList(List<SessionDtoRequest> sessionDtoRequests);
}