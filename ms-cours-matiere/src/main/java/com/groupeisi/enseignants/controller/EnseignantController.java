package com.groupeisi.enseignants.controller;

import com.groupeisi.enseignants.dto.requests.EnseignantDtoRequest;
import com.groupeisi.enseignants.dto.responses.EnseignantDtoResponse;
import com.groupeisi.enseignants.service.EnseignantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/enseignants")
public class EnseignantController {
    private final EnseignantService enseignantService;

    @PostMapping
    public ResponseEntity<EnseignantDtoResponse> createEnseignant(@RequestBody @Valid EnseignantDtoRequest enseignantDtoRequest) {
        Optional<EnseignantDtoResponse> savedEnseignant = enseignantService.saveEnseignant(enseignantDtoRequest);
        return savedEnseignant
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @GetMapping
    public ResponseEntity<List<EnseignantDtoResponse>> getAllEnseignants() {
        Optional<List<EnseignantDtoResponse>> enseignantList = enseignantService.getAllEnseignants();
        return new ResponseEntity<>(enseignantList.get(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EnseignantDtoResponse> getEnseignant(@PathVariable("id") Long id) {
        Optional<EnseignantDtoResponse> enseignant = enseignantService.getEnseignantById(id);
        return enseignant
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EnseignantDtoResponse> updateEnseignant(@PathVariable("id") Long id, @RequestBody @Valid EnseignantDtoRequest enseignantDtoRequest) {
        enseignantDtoRequest.setId(id);
        try {
            Optional<EnseignantDtoResponse> updatedEnseignant = enseignantService.updateEnseignant(enseignantDtoRequest);
            return new ResponseEntity<>(updatedEnseignant.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable("id") Long id) {
        try {
            boolean deleted = enseignantService.deleteEnseignant(id);
            return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
