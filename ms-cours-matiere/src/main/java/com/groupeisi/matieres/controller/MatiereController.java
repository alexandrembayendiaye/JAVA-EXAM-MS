package com.groupeisi.matieres.controller;


import com.groupeisi.matieres.dto.requests.MatiereDtoRequest;
import com.groupeisi.matieres.dto.responses.MatiereDtoResponse;
import com.groupeisi.matieres.service.MatiereService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/matieres")
public class MatiereController {
    private final MatiereService matiereService;
    @PostMapping
    public ResponseEntity<MatiereDtoResponse> createMatiere(@RequestBody @Valid MatiereDtoRequest matiereDtoRequest) {
        Optional<MatiereDtoResponse> savedMatiere = matiereService.saveMatiere(matiereDtoRequest);
        return savedMatiere
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @GetMapping
    public ResponseEntity<List<MatiereDtoResponse>> getAllMatieres() {
        Optional<List<MatiereDtoResponse>> matiereList = matiereService.getAllMatieres();
        return new ResponseEntity<>(matiereList.get(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MatiereDtoResponse> getMatiere(@PathVariable("id") Long id) {
        Optional<MatiereDtoResponse> matiere = matiereService.getMatiereById(id);
        return matiere
                .map(m -> new ResponseEntity<>(m, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatiereDtoResponse> updateMatiere(@PathVariable("id") Long id, @RequestBody @Valid MatiereDtoRequest matiereDtoRequest) {
        matiereDtoRequest.setId(id);
        try {
            Optional<MatiereDtoResponse> updatedMatiere = matiereService.updateMatiere(matiereDtoRequest);
            return new ResponseEntity<>(updatedMatiere.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable("id") Long id) {
        try {
            boolean deleted = matiereService.deleteMatiere(id);
            return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
