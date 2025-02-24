package com.groupeisi.matieres.service.impl;


import com.groupeisi.exception.EntityExistsException;
import com.groupeisi.exception.EntityNotFoundException;
import com.groupeisi.matieres.dto.requests.MatiereDtoRequest;
import com.groupeisi.matieres.dto.responses.MatiereDtoResponse;
import com.groupeisi.matieres.entities.Matiere;
import com.groupeisi.matieres.mapper.MatiereMapper;
import com.groupeisi.matieres.repository.MatiereRepository;
import com.groupeisi.matieres.service.MatiereService;
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
public class MatiereServiceImpl implements MatiereService {
    private final MatiereRepository matiereRepository;
    private final MatiereMapper matiereMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public Optional<MatiereDtoResponse> saveMatiere(MatiereDtoRequest matiereDtoRequest) {
        // Vérification d'existence par le nom
        if (matiereRepository.findByNom(matiereDtoRequest.getNom()).isPresent()){
            throw new EntityExistsException(messageSource.getMessage("matiere.exists", new Object[]{matiereDtoRequest.getNom()}, Locale.getDefault()));
        }
        var matiere = matiereRepository.save(matiereMapper.toEntity(matiereDtoRequest));
        return Optional.of(matiereMapper.toDto(matiere));
    }
    @Override
    public Optional<List<MatiereDtoResponse>> getAllMatieres() {
        List<Matiere> matiereList = matiereRepository.findAll();
        return Optional.of(matiereMapper.toDtoList(matiereList));
    }
    @Override
    public Optional<MatiereDtoResponse> getMatiereById(Long id) {
        return matiereRepository.findById(id)
                .map(matiere -> Optional.of(matiereMapper.toDto(matiere)))
                .orElseThrow(() -> new EntityExistsException(messageSource.getMessage("matiere.notfound", new Object[]{id}, Locale.getDefault())));
    }


    @Override
    public Optional<MatiereDtoResponse> updateMatiere(MatiereDtoRequest matiereDtoRequest) {
        return matiereRepository.findById(matiereDtoRequest.getId())
                .map(matiere -> {
                    matiere.setNom(matiereDtoRequest.getNom());
                    matiere.setDescription(matiereDtoRequest.getDescription());
                    matiere.setArchive(matiereDtoRequest.isArchive());
                    var updateMatiere = matiereRepository.save(matiere);
                    return Optional.of(matiereMapper.toDto(updateMatiere));
                })
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("matiere.notfound", new Object[]{matiereDtoRequest.getId()}, Locale.getDefault())));
    }

    @Override
    public boolean deleteMatiere(Long id) {
        if (matiereRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage("matiere.notfound", new Object[]{id}, Locale.getDefault()));
        }
        matiereRepository.deleteById(id);
        return true;
    }
}
