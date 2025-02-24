package com.groupeisi.enseignants.service;

import com.groupeisi.enseignants.dto.requests.EnseignantDtoRequest;
import com.groupeisi.enseignants.dto.responses.EnseignantDtoResponse;

import java.util.List;
import java.util.Optional;

public interface EnseignantService {
    Optional<EnseignantDtoResponse> saveEnseignant(EnseignantDtoRequest enseignantDtoRequest);
    Optional<List<EnseignantDtoResponse>> getAllEnseignants();
    Optional<EnseignantDtoResponse> getEnseignantById(Long id);
    Optional<EnseignantDtoResponse> updateEnseignant(EnseignantDtoRequest enseignantDtoRequest);
    boolean deleteEnseignant(Long id);
}
