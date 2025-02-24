package com.groupeisi.sessions.service;

import com.groupeisi.sessions.dto.requests.SessionDtoRequest;
import com.groupeisi.sessions.dto.responses.SessionDtoResponse;

import java.util.List;
import java.util.Optional;

public interface SessionService {

    Optional<SessionDtoResponse> saveSession(SessionDtoRequest sessionDtoRequest);
    Optional<List<SessionDtoResponse>> getAllSessions();
    Optional<SessionDtoResponse> getSessionById(Long id);
    Optional<SessionDtoResponse> updateSession(SessionDtoRequest sessionDtoRequest);
    boolean deleteSession(Long id);
}