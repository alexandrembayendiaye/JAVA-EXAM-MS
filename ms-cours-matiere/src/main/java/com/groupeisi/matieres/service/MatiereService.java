package com.groupeisi.matieres.service;

import com.groupeisi.matieres.dto.requests.MatiereDtoRequest;
import com.groupeisi.matieres.dto.responses.MatiereDtoResponse;

import java.util.List;
import java.util.Optional;

public interface MatiereService {

    Optional<MatiereDtoResponse> saveMatiere(MatiereDtoRequest matiereDtoRequest);
    Optional<List<MatiereDtoResponse>> getAllMatieres();
    Optional<MatiereDtoResponse> getMatiereById(Long id);
    Optional<MatiereDtoResponse> updateMatiere(MatiereDtoRequest matiereDtoRequest);
    boolean deleteMatiere(Long id);
}