package com.groupeisi.sessions.service.impl;

import com.groupeisi.exception.EntityExistsException;
import com.groupeisi.exception.EntityNotFoundException;
import com.groupeisi.sessions.dto.requests.SessionDtoRequest;
import com.groupeisi.sessions.dto.responses.SessionDtoResponse;
import com.groupeisi.sessions.entities.Session;
import com.groupeisi.sessions.mapper.SessionMapper;
import com.groupeisi.sessions.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private SessionMapper sessionMapper;
    @Mock
    private MessageSource messageSource;

    private SessionDtoRequest sessionDtoRequest;
    private SessionDtoResponse sessionDtoResponse;
    private Session session;
    @BeforeEach
    void setUp() {
        sessionDtoRequest = new SessionDtoRequest();
        sessionDtoRequest.setId(1L);
        sessionDtoRequest.setNom("Session Printemps 2025");
        sessionDtoRequest.setDescription("Session pour le semestre de printemps");
        sessionDtoRequest.setArchive(false);

        sessionDtoResponse = new SessionDtoResponse();
        sessionDtoResponse.setId(1L);
        sessionDtoResponse.setNom("Session Printemps 2025");
        sessionDtoResponse.setDescription("Session pour le semestre de printemps");
        sessionDtoResponse.setArchive(false);

        session = new Session();
        session.setId(1L);
        session.setNom("Session Printemps 2025");
        session.setDescription("Session pour le semestre de printemps");
        session.setArchive(false);
    }
    @Test
    void saveSessionOK() {
        when(sessionRepository.findByNom(sessionDtoRequest.getNom())).thenReturn(Optional.empty());
        when(sessionMapper.toEntity(sessionDtoRequest)).thenReturn(session);
        when(sessionRepository.save(session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDtoResponse);

        Optional<SessionDtoResponse> savedSession = sessionService.saveSession(sessionDtoRequest);

        assertTrue(savedSession.isPresent());
        assertEquals("Session Printemps 2025", savedSession.get().getNom());
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void saveSessionKO() {
        when(sessionRepository.findByNom(sessionDtoRequest.getNom())).thenReturn(Optional.of(session));
        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("La session avec le nom = {0} existe déjà.");

        assertThrows(EntityExistsException.class, () -> sessionService.saveSession(sessionDtoRequest));
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void getAllSessions() {
        when(sessionRepository.findAll()).thenReturn(List.of(session));
        when(sessionMapper.toDtoList(List.of(session))).thenReturn(List.of(sessionDtoResponse));

        Optional<List<SessionDtoResponse>> sessionsList = sessionService.getAllSessions();

        assertTrue(sessionsList.isPresent());
        assertEquals(1, sessionsList.get().size());
        assertEquals("Session Printemps 2025", sessionsList.get().get(0).getNom());
    }

    @Test
    void getSessionByIdOK() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(sessionMapper.toDto(session)).thenReturn(sessionDtoResponse);

        Optional<SessionDtoResponse> result = sessionService.getSessionById(1L);

        assertTrue(result.isPresent());
        assertEquals("Session Printemps 2025", result.get().getNom());
    }

//    @Test
//    void getSessionByIdKO() {
//        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
//        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("Session non trouvée");
//
//        assertThrows(EntityNotFoundException.class, () -> sessionService.getSessionById(1L));
//    }

    @Test
    void updateSessionOK() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDtoResponse);

        Optional<SessionDtoResponse> updatedSession = sessionService.updateSession(sessionDtoRequest);

        assertTrue(updatedSession.isPresent());
        assertEquals("Session Printemps 2025", updatedSession.get().getNom());
    }

    @Test
    void updateSessionKO() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("Session non trouvée");

        assertThrows(EntityNotFoundException.class, () -> sessionService.updateSession(sessionDtoRequest));
    }

    @Test
    void deleteSessionOK() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        doNothing().when(sessionRepository).deleteById(1L);

        boolean deleted = sessionService.deleteSession(1L);

        assertTrue(deleted);
        verify(sessionRepository, times(1)).deleteById(1L);
    }
}