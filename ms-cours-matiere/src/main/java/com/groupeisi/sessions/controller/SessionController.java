package com.groupeisi.sessions.controller;


import com.groupeisi.sessions.dto.requests.SessionDtoRequest;
import com.groupeisi.sessions.dto.responses.SessionDtoResponse;
import com.groupeisi.sessions.service.SessionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionDtoResponse> createSession(@RequestBody @Valid SessionDtoRequest sessionDtoRequest) {
        Optional<SessionDtoResponse> savedSession = sessionService.saveSession(sessionDtoRequest);
        return savedSession
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @GetMapping
    public ResponseEntity<List<SessionDtoResponse>> getAllSessions() {
        Optional<List<SessionDtoResponse>> sessionList = sessionService.getAllSessions();
        return new ResponseEntity<>(sessionList.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDtoResponse> getSession(@PathVariable("id") Long id) {
        Optional<SessionDtoResponse> session = sessionService.getSessionById(id);
        return session
                .map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<SessionDtoResponse> updateSession(@PathVariable("id") Long id, @RequestBody @Valid SessionDtoRequest sessionDtoRequest) {
        sessionDtoRequest.setId(id);
        try {
            Optional<SessionDtoResponse> updatedSession = sessionService.updateSession(sessionDtoRequest);
            return new ResponseEntity<>(updatedSession.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable("id") Long id) {
        try {
            boolean deleted = sessionService.deleteSession(id);
            return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
