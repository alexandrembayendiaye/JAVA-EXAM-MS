package com.groupeisi.cours.controller;

import com.groupeisi.cours.dto.requests.CoursDtoRequest;
import com.groupeisi.cours.dto.responses.CoursDtoResponse;
import com.groupeisi.cours.service.CoursService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/cours")
public class CoursController {
    private final CoursService coursService;

    @PostMapping
    public ResponseEntity<CoursDtoResponse> createCours(@RequestBody @Valid CoursDtoRequest coursDtoRequest) {
        Optional<CoursDtoResponse> savedCours = coursService.saveCours(coursDtoRequest);
        return savedCours
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @GetMapping
    public ResponseEntity<List<CoursDtoResponse>> getAllCours() {
        Optional<List<CoursDtoResponse>> coursList = coursService.getAllCours();
        return new ResponseEntity<>(coursList.get(), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CoursDtoResponse> getCours(@PathVariable("id") Long id) {
        Optional<CoursDtoResponse> cours = coursService.getCoursById(id);
        return cours
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CoursDtoResponse> updateCours(@PathVariable("id") Long id, @RequestBody @Valid CoursDtoRequest coursDtoRequest) {
        coursDtoRequest.setId(id);
        try {
            Optional<CoursDtoResponse> updatedCours = coursService.updateCours(coursDtoRequest);
            return new ResponseEntity<>(updatedCours.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCours(@PathVariable("id") Long id) {
        try {
            boolean deleted = coursService.deleteCours(id);
            return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
