package com.groupeisi.sessions.service.impl;

import com.groupeisi.exception.EntityExistsException;
import com.groupeisi.exception.EntityNotFoundException;
import com.groupeisi.sessions.dto.requests.SessionDtoRequest;
import com.groupeisi.sessions.dto.responses.SessionDtoResponse;
import com.groupeisi.sessions.entities.Session;
import com.groupeisi.sessions.mapper.SessionMapper;
import com.groupeisi.sessions.repository.SessionRepository;
import com.groupeisi.sessions.service.SessionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final MessageSource messageSource;
    @Override
    @Transactional
    public Optional<SessionDtoResponse> saveSession(SessionDtoRequest sessionDtoRequest) {
        // Vérification d'existence par le nom
        if (sessionRepository.findByNom(sessionDtoRequest.getNom()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("session.exists", new Object[]{sessionDtoRequest.getNom()}, Locale.getDefault()));
        }
        var session = sessionRepository.save(sessionMapper.toEntity(sessionDtoRequest));
        return Optional.of(sessionMapper.toDto(session));
    }


    @Override
    public Optional<List<SessionDtoResponse>> getAllSessions() {
        List<Session> sessionList = sessionRepository.findAll();
        return Optional.of(sessionMapper.toDtoList(sessionList));
    }

    @Override
    public Optional<SessionDtoResponse> getSessionById(Long id) {
        return sessionRepository.findById(id)
                .map(session -> Optional.of(sessionMapper.toDto(session)))
                .orElseThrow(() -> new EntityExistsException(messageSource.getMessage("session.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public Optional<SessionDtoResponse> updateSession(SessionDtoRequest sessionDtoRequest) {
        return sessionRepository.findById(sessionDtoRequest.getId())
                .map(session -> {
                    session.setNom(sessionDtoRequest.getNom());
                    session.setDateDebut(sessionDtoRequest.getDateDebut());
                    session.setDateFin(sessionDtoRequest.getDateFin());
                    session.setDescription(sessionDtoRequest.getDescription());
                    session.setArchive(sessionDtoRequest.isArchive());
                    var updatedSession = sessionRepository.save(session);
                    return Optional.of(sessionMapper.toDto(updatedSession));
                })
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("session.notfound", new Object[]{sessionDtoRequest.getId()}, Locale.getDefault())));
    }

    @Override
    public boolean deleteSession(Long id) {
        if (sessionRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage("session.notfound", new Object[]{id}, Locale.getDefault()));
        }
        sessionRepository.deleteById(id);
        return true;
    }
}
